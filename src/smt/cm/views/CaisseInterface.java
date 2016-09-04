package smt.cm.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import smt.cm.controllers.CaisseRepository;
import smt.cm.controllers.CaisseSessionRepository;
import smt.cm.controllers.EmployeeRepository;
import smt.cm.controllers.ProductRepository;
import smt.cm.entities.Caisse;
import smt.cm.entities.CaisseSession;
import smt.cm.entities.Employee;
import smt.cm.entities.Product;
import smt.cm.entities.Ticket;
import smt.cm.util.AppConst;
import smt.cm.util.HibernateUtil;
import smt.services.Services;

public class CaisseInterface extends JFrame {
	private static final long serialVersionUID = 6121113813214104387L;

	private final String PRODUCT_TICKET_FACADE = "PRODUCT_TICKET_FACADE";
	private final String PAIEMENT_FACADE = "PAIEMENT_FACADE";
	private final String CAISSE_FACADE = "CAISSE_FACADE";
	private final String AUTHENTIFICATION_FACADE = "AUTHENTIFICATION_FACADE";
	private final String SUCCESS_AUTHENTIFICATION_FACADE = "SUCCESS_AUTHENTIFICATION_FACADE";
	private final String STANDBY_FACADE = "STANDBY_FACADE";

	private JPanel mainCardContainer;
	private CardLayout mainCardLayout;
	
	private JPanel caissePanel;
	private JPanel caisseCardContainer;
	private CardLayout caissCardLayout;
	private JPanel caisseFacadePanel;
	private PayTicketPanel paimentPanel;
	private TicketDetailsPanel ticketDetailsPanel;
	private ProductsContainer productsContainer;
	private WaitingTicketDialog waitingTicketDialog;
	private HeaderCaissPanel headerCaissePanel;
	private AuthentificationPanel authentificationPanel;
	private SuccessAuthentificationPanel successAuthentificationPanel;
	private StandByPanel standByPanel;

	// -----init repositories-----------
	private ProductRepository productRepository = new ProductRepository();
	private CaisseSessionRepository caisseSessionRepository = new CaisseSessionRepository();
	private CaisseRepository caisseRepository = new CaisseRepository();
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	private List<Product> products;
	private List<Ticket> waitingTickets = new ArrayList<Ticket>();
	public static CaisseSession CAISSE_SESSION;
	
	private JComponent lastFocusedComponent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Services.setDefaultOSStyle();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new HibernateUtil();
					CaisseInterface frame = new CaisseInterface();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CaisseInterface() {
		setTitle("SMART Caisse");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1010, 576);
		
		WindowListener existListner = new WindowAdapter() {
    		@Override
            public void windowClosing(WindowEvent e) {   			
				closeCaisse();
            }
		};
		this.addWindowListener(existListner);
		
		mainCardContainer = new JPanel();
		mainCardLayout = new CardLayout(0, 0);
		mainCardContainer.setLayout(mainCardLayout);
		setContentPane(mainCardContainer);
		
		authentificationPanel = new AuthentificationPanel(this);
		mainCardContainer.add(authentificationPanel, AUTHENTIFICATION_FACADE);
		
		successAuthentificationPanel = new SuccessAuthentificationPanel(this);
		mainCardContainer.add(successAuthentificationPanel, SUCCESS_AUTHENTIFICATION_FACADE);
		
		standByPanel = new StandByPanel(this);
		mainCardContainer.add(standByPanel, STANDBY_FACADE);
		
		caissePanel = new JPanel();
		caissePanel.setAlignmentY(0.0f);
		caissePanel.setAlignmentX(0.0f);
		caissePanel.setBorder(null);
		caissePanel.setLayout(new BorderLayout(0, 0));
		mainCardContainer.add(caissePanel, CAISSE_FACADE);

		caissCardLayout = new CardLayout(0, 0);
		caisseCardContainer = new JPanel();
		caisseCardContainer.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		caissePanel.add(caisseCardContainer, BorderLayout.CENTER);
		caisseCardContainer.setLayout(caissCardLayout);

		caisseFacadePanel = new JPanel();
		caisseCardContainer.add(caisseFacadePanel, PRODUCT_TICKET_FACADE);
		caisseFacadePanel.setLayout(new BorderLayout(0, 0));

		ticketDetailsPanel = new TicketDetailsPanel(this);
		caisseFacadePanel.add(ticketDetailsPanel, BorderLayout.EAST);

		productsContainer = new ProductsContainer(this);
		caisseFacadePanel.add(productsContainer, BorderLayout.CENTER);

		paimentPanel = new PayTicketPanel(this);
		caisseCardContainer.add(paimentPanel, PAIEMENT_FACADE);

		waitingTicketDialog = new WaitingTicketDialog(this);

		headerCaissePanel = new HeaderCaissPanel(CaisseInterface.this);
		headerCaissePanel.setPreferredSize(new Dimension(785, 65));
		caissePanel.add(headerCaissePanel, BorderLayout.NORTH);				

//		initData();
	}

	/**
	 * Init data for caisse
	 */
	private void initData() {
		products = productRepository.read();
		productsContainer.initListProductsAndCategories(products);
		paimentPanel.initRegulationModes();
	}

	/**
	 * Add selected product to ticket
	 * 
	 * @param product
	 */
	public void sendProductToTicket(Product product) {
		ticketDetailsPanel.addProductToTicket(product);
	}

	public void sendTicketForPaiement(Ticket ticket) {
		caissCardLayout.show(caisseCardContainer, PAIEMENT_FACADE);
		paimentPanel.payTicket(ticket);
	}

	public void getBackToCaissFacade() {
		caissCardLayout.show(caisseCardContainer, PRODUCT_TICKET_FACADE);
	}

	public void prepeareNewCommand() {
		ticketDetailsPanel.initPanel();
		productsContainer.initPanel();
		caissCardLayout.show(caisseCardContainer, PRODUCT_TICKET_FACADE);
		productsContainer.focusBarcodeField();
	}

	public void addTicketToWaitingList(Ticket ticket) {
		waitingTickets.add(ticket);
		headerCaissePanel.setNumbeOfWaitingTickets(waitingTickets.size());
	}

	public void displayWaitingTickets() {
		if (waitingTickets.size() == 0) {
			JOptionPane.showMessageDialog(this,
					"Aucune ticket n'est mis en attente !", "Aucune ticket",
					JOptionPane.ERROR_MESSAGE);
			
		} else if(waitingTickets.size() == 1){
			Ticket selectedTicket = waitingTickets.get(0);
			waitingTickets.remove(0);
			headerCaissePanel.setNumbeOfWaitingTickets(waitingTickets.size());
			ticketDetailsPanel.resetWaitingTicket(selectedTicket);
		}else {
			waitingTicketDialog.setListAndShow(waitingTickets);
			if (waitingTicketDialog.getSelectedTicket() != null) {
				removeTicketsFromWaitingList(waitingTicketDialog.getSelectedTicket());
				headerCaissePanel.setNumbeOfWaitingTickets(waitingTickets.size());
				ticketDetailsPanel.resetWaitingTicket(waitingTicketDialog.getSelectedTicket());
			}
		}
	}

	/**
	 * Remove ticket from waiting list
	 * 
	 * @param selectedTicket
	 */
	private void removeTicketsFromWaitingList(Ticket selectedTicket) {
		int pos = 0;
		for (Ticket ticket : waitingTickets) {
			if (selectedTicket.equals(ticket)) {
				waitingTickets.remove(pos);
				break;
			}
			pos++;
		}
	}

	public void openCaisse() {
		CAISSE_SESSION.getCaisse().setStat(AppConst.CAISSE_ACTIV);
		caisseRepository.update(CAISSE_SESSION.getCaisse());
		mainCardLayout.show(mainCardContainer, CAISSE_FACADE);
		productsContainer.focusBarcodeField();
	}

	public JComponent getLastFocusedComponent() {
		return lastFocusedComponent;
	}

	public void setLastFocusedComponent(JComponent lastFocusedComponent) {
		this.lastFocusedComponent = lastFocusedComponent;
	}

	/**
	 * Start authentification
	 * @param fond
	 * @param password
	 */
	public void startAuthentification(double fond, String password) {
		Employee employee = employeeRepository.findBy("caissePassword", password);
		if(employee != null){
			initCaisse(fond, employee);
		}else{
			JOptionPane.showMessageDialog(this, "Veuillez vérifier votre mot de passe!", "Accès refusé", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initilize caisse
	 * @param fond
	 * @param employee
	 */
	private void initCaisse(double fond, Employee employee) {
		successAuthentificationPanel.setEmployeeName(employee.getName());
		mainCardLayout.show(mainCardContainer, SUCCESS_AUTHENTIFICATION_FACADE);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Caisse caisse = caisseRepository.findBy("ipAdress", "192.168.1.100");
				CAISSE_SESSION = new CaisseSession();
				CAISSE_SESSION.setCaisse(caisse);
				CAISSE_SESSION.setEmployee(employee);
				CAISSE_SESSION.setStartFond(fond);
				CAISSE_SESSION.setOpenDate(new Date());
				CAISSE_SESSION.setId(caisseSessionRepository.create(CAISSE_SESSION));		
				headerCaissePanel.setUserName(employee.getName());
				initData();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				openCaisse();
			}
		});
		thread.start();
	}

	/**
	 * Set caisse in stand by mode
	 */
	public void setStandByMode() {
		standByPanel.setUserName(CAISSE_SESSION.getEmployee().getName());
		CAISSE_SESSION.getCaisse().setStat(AppConst.CAISSE_SLEEP);
		caisseRepository.update(CAISSE_SESSION.getCaisse());
		mainCardLayout.show(mainCardContainer, STANDBY_FACADE);
	}

	/**
	 * Close caisse
	 */
	public void closeCaisse() {
		int res = JOptionPane.showConfirmDialog(this, "Confirmez-vous la clôture de cette caisse ?", "Clôtuer Caisse", JOptionPane.ERROR_MESSAGE);
		if(res == JOptionPane.YES_OPTION){
			CAISSE_SESSION.setCloseDate(new Date());
			CAISSE_SESSION.getCaisse().setStat(AppConst.CAISSE_CLOSED);
			caisseSessionRepository.update(CAISSE_SESSION);
			caisseRepository.update(CAISSE_SESSION.getCaisse());
			System.exit(0);
		}
	}

	public void focusBarcodeField() {
		productsContainer.focusBarcodeField();
	}
}

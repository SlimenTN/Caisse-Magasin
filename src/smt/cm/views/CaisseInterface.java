package smt.cm.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import smt.cm.controllers.ProductRepository;
import smt.cm.entities.Product;
import smt.cm.entities.Ticket;
import smt.cm.util.HibernateUtil;
import smt.services.Services;

public class CaisseInterface extends JFrame {
	private static final long serialVersionUID = 6121113813214104387L;
	
	private final String CAISSE_FACADE = "CAISSE_FACADE";
	private final String PAIEMENT_FACADE = "PAIEMENT_FACADE";
	
	private JPanel contentPane;
	private JPanel cardContainer;
	private CardLayout cardLayout;
	private JPanel caisseFacadePanel;
	private PayTicketPanel paimentPanel;
	private TicketDetailsPanel ticketDetailsPanel;
	private ProductsContainer productsContainer;
	
	//-----init repositories-----------
	private ProductRepository productRepository = new ProductRepository();

	private List<Product> products;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 576);
		contentPane = new JPanel();
		contentPane.setAlignmentY(0.0f);
		contentPane.setAlignmentX(0.0f);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(13, 50));
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		cardLayout = new CardLayout(0, 0);
		cardContainer = new JPanel();
		contentPane.add(cardContainer, BorderLayout.CENTER);
		cardContainer.setLayout(cardLayout);
		
		caisseFacadePanel = new JPanel();
		cardContainer.add(caisseFacadePanel, CAISSE_FACADE);
		caisseFacadePanel.setLayout(new BorderLayout(0, 0));
		
		ticketDetailsPanel = new TicketDetailsPanel(this);
		caisseFacadePanel.add(ticketDetailsPanel, BorderLayout.EAST);
		
		productsContainer = new ProductsContainer(this);
		caisseFacadePanel.add(productsContainer, BorderLayout.CENTER);
		
		paimentPanel = new PayTicketPanel(this);
		cardContainer.add(paimentPanel, PAIEMENT_FACADE);
		
		initData();
	}	
	
	/**
	 * Init data for caisse
	 */
	private void initData() {
		products = productRepository.read();
		productsContainer.initListProductsAndCategories(products);		
		
	}

	/**
	 * Add selected product to ticket
	 * @param product
	 */
	public void sendProductToTicket(Product product){
		ticketDetailsPanel.addProductToTicket(product);
	}

	public void sendTicketForPaiement(Ticket ticket) {
		cardLayout.show(cardContainer, PAIEMENT_FACADE);
		paimentPanel.payTicket(ticket);
		System.err.println("dqsd");
	}

	public void getBackToCaissFacade() {
		cardLayout.show(cardContainer, CAISSE_FACADE);
	}

	public void prepeareNewCommand() {		
		ticketDetailsPanel.initPanel();
		productsContainer.initPanel();
		cardLayout.show(cardContainer, CAISSE_FACADE);
	}
	
	
}

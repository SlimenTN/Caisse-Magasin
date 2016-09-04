package smt.cm.views;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.MatteBorder;

import smt.cm.comps.CashPayPanel;
import smt.cm.comps.ModeRegulationButton;
import smt.cm.comps.NumpadButton;
import smt.cm.controllers.ModeRegulationRepository;
import smt.cm.controllers.Repository;
import smt.cm.controllers.TicketRepository;
import smt.cm.entities.ModeRegulation;
import smt.cm.entities.Ticket;
import smt.cm.entities.TicketHasModes;
import smt.cm.entities.TicketLine;
import smt.cm.interfaces.ModePaiment;
import smt.cm.util.AppConst;
import smt.services.Services;

public class PayTicketPanel extends JPanel {

	private static final long serialVersionUID = 7883904567906327358L;
	
	private NumpadButton numPad1;
	private NumpadButton numPad2;
	private NumpadButton numPad3;
	private NumpadButton numPad4;
	private NumpadButton numPad5;
	private NumpadButton numPad6;
	private NumpadButton numPad7;
	private NumpadButton numPad8;
	private NumpadButton numPad9;
	private NumpadButton numPad0;
	private NumpadButton numPadReset;
	private NumpadButton numPadPoint;
	private NumpadButton numPadRollBack;
	private NumpadButton numPadPlus10;
	private NumpadButton numPadPlus20;
	private NumpadButton numPadPlus50;
	private NumpadButton btnBacktoTicket;
	private NumpadButton btnPrintAndConfirm;
	private JLabel lblTotTicket;
	private JLabel lblPayedMoney;
	private JLabel lblRestMoney;

	private CaisseInterface caisseInterface;
	private Box paimentModebox;
	private JPanel paimentPanel;

	private Ticket ticket = null;
	private NumpadButton btnCancelTicket;

	private JComponent lastFocusedComponent = null;

	private Robot numpadRobot;
	
	private TicketRepository ticketRepository = new TicketRepository();
	private ModeRegulationRepository modeRegulationRepository = new ModeRegulationRepository();
	private JPanel modeRegulationPanel;
	private SwingWorker<Void, Void> saveWorker;

	public PayTicketPanel(CaisseInterface caisseInterface) {
		this.caisseInterface = caisseInterface;
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(330, 10));
		add(panel, BorderLayout.EAST);
		panel.setLayout(null);

		numPad1 = new NumpadButton("1");
		numPad1.setBounds(10, 11, 70, 70);
		numPad1.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD1));
		panel.add(numPad1);

		numPad2 = new NumpadButton("2");
		numPad2.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD2));
		numPad2.setBounds(90, 11, 70, 70);
		panel.add(numPad2);

		numPad3 = new NumpadButton("3");
		numPad3.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD3));
		numPad3.setBounds(170, 11, 70, 70);
		panel.add(numPad3);

		numPadPlus10 = new NumpadButton("+10");
		numPadPlus10.addActionListener(e -> appendMoney(10));
		numPadPlus10.setBounds(250, 11, 70, 70);
		panel.add(numPadPlus10);

		numPad4 = new NumpadButton("4");
		numPad4.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD4));
		numPad4.setBounds(10, 92, 70, 70);
		panel.add(numPad4);

		numPad5 = new NumpadButton("5");
		numPad5.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD5));
		numPad5.setBounds(90, 92, 70, 70);
		panel.add(numPad5);

		numPad6 = new NumpadButton("6");
		numPad6.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD6));
		numPad6.setBounds(170, 92, 70, 70);
		panel.add(numPad6);

		numPadPlus20 = new NumpadButton("+20");
		numPadPlus20.addActionListener(e -> appendMoney(20));
		numPadPlus20.setBounds(250, 92, 70, 70);
		panel.add(numPadPlus20);

		numPad7 = new NumpadButton("7");
		numPad7.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD7));
		numPad7.setBounds(10, 173, 70, 70);
		panel.add(numPad7);

		numPad8 = new NumpadButton("8");
		numPad8.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD8));
		numPad8.setBounds(90, 173, 70, 70);
		panel.add(numPad8);

		numPad9 = new NumpadButton("9");
		numPad9.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD9));
		numPad9.setBounds(170, 173, 70, 70);
		panel.add(numPad9);

		numPadPlus50 = new NumpadButton("+50");
		numPadPlus50.addActionListener(e -> appendMoney(50));
		numPadPlus50.setBounds(250, 173, 70, 70);
		panel.add(numPadPlus50);

		numPadReset = new NumpadButton("C");
		numPadReset.addActionListener(e -> clearMoney());
		numPadReset.setBounds(10, 254, 70, 70);
		panel.add(numPadReset);

		numPad0 = new NumpadButton("0");
		numPad0.addActionListener(e -> numpadFired(KeyEvent.VK_NUMPAD0));
		numPad0.setBounds(90, 254, 70, 70);
		panel.add(numPad0);

		numPadPoint = new NumpadButton(".");
		numPadPoint.addActionListener(e -> numpadFired(KeyEvent.VK_DECIMAL));
		numPadPoint.setBounds(170, 254, 70, 70);
		panel.add(numPadPoint);

		numPadRollBack = new NumpadButton("<-");
		numPadRollBack.addActionListener(e -> numpadFired(KeyEvent.VK_BACK_SPACE));
		numPadRollBack.setBounds(250, 254, 70, 70);
		panel.add(numPadRollBack);

		btnBacktoTicket = new NumpadButton("Retour");
		btnBacktoTicket.addActionListener(e -> rollBackTicket());
		btnBacktoTicket.setBounds(10, 335, 150, 70);
		panel.add(btnBacktoTicket);

		btnCancelTicket = new NumpadButton("Annuler");
		btnCancelTicket.setText("Annuler");
		btnCancelTicket.setBounds(170, 335, 150, 70);
		panel.add(btnCancelTicket);

		btnPrintAndConfirm = new NumpadButton("Imprimer et continuer");
		btnPrintAndConfirm.addActionListener(e -> confirmCommand());
		btnPrintAndConfirm.setBounds(10, 416, 310, 152);
		panel.add(btnPrintAndConfirm);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		modeRegulationPanel = new JPanel();
		modeRegulationPanel.setBackground(new Color(255, 255, 255));
		FlowLayout fl_modeRegulationPanel = (FlowLayout) modeRegulationPanel.getLayout();
		fl_modeRegulationPanel.setAlignment(FlowLayout.LEFT);
		modeRegulationPanel.setPreferredSize(new Dimension(10, 100));
		panel_1.add(modeRegulationPanel, BorderLayout.NORTH);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(143, 188, 143));
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		lblTotTicket = new JLabel("78,580 DT");
		lblTotTicket.setForeground(new Color(255, 255, 255));
		lblTotTicket.setFont(new Font("Century Gothic", Font.PLAIN, 80));
		lblTotTicket.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblTotTicket, BorderLayout.NORTH);

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setPreferredSize(new Dimension(100, 10));
		panel_4.add(panel_7, BorderLayout.WEST);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 24, 0, 0, 0 };
		gbl_panel_7.rowHeights = new int[] { 14, 0, 0, 0 };
		gbl_panel_7.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

		JLabel lblPay = new JLabel("Pay\u00E9");
		lblPay.setHorizontalAlignment(SwingConstants.CENTER);
		lblPay.setFont(new Font("Century Gothic", Font.BOLD, 16));
		GridBagConstraints gbc_lblPay = new GridBagConstraints();
		gbc_lblPay.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPay.insets = new Insets(0, 0, 5, 5);
		gbc_lblPay.anchor = GridBagConstraints.NORTH;
		gbc_lblPay.gridx = 1;
		gbc_lblPay.gridy = 0;
		panel_7.add(lblPay, gbc_lblPay);

		lblPayedMoney = new JLabel("80,000");
		lblPayedMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPayedMoney.setFont(new Font("Century Gothic", Font.BOLD, 25));
		GridBagConstraints gbc_lblPayedMoney = new GridBagConstraints();
		gbc_lblPayedMoney.insets = new Insets(0, 0, 5, 5);
		gbc_lblPayedMoney.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPayedMoney.gridx = 1;
		gbc_lblPayedMoney.gridy = 1;
		panel_7.add(lblPayedMoney, gbc_lblPayedMoney);

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		panel_8.setPreferredSize(new Dimension(100, 10));
		panel_4.add(panel_8, BorderLayout.EAST);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_8.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_8.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_8.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_8.setLayout(gbl_panel_8);

		JLabel lblRendu = new JLabel("Rendu");
		lblRendu.setHorizontalAlignment(SwingConstants.CENTER);
		lblRendu.setFont(new Font("Century Gothic", Font.BOLD, 16));
		GridBagConstraints gbc_lblRendu = new GridBagConstraints();
		gbc_lblRendu.insets = new Insets(0, 0, 5, 5);
		gbc_lblRendu.gridx = 1;
		gbc_lblRendu.gridy = 0;
		panel_8.add(lblRendu, gbc_lblRendu);

		lblRestMoney = new JLabel("1,420");
		lblRestMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRestMoney.setFont(new Font("Century Gothic", Font.BOLD, 25));
		GridBagConstraints gbc_lblRestMoney = new GridBagConstraints();
		gbc_lblRestMoney.gridx = 1;
		gbc_lblRestMoney.gridy = 1;
		panel_8.add(lblRestMoney, gbc_lblRestMoney);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_4.add(panel_5, BorderLayout.SOUTH);

		paimentPanel = new JPanel();
		paimentPanel.setBackground(Color.WHITE);
		panel_4.add(paimentPanel, BorderLayout.CENTER);

		paimentModebox = Box.createVerticalBox();
		paimentPanel.add(paimentModebox);

		try {
			numpadRobot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Confirm command and pass to the next one
	 */
	private void confirmCommand() {
		for (TicketLine line : ticket.getTicketLines()) {
			System.out.println(line.getProduct().getDesignation());
		}
		if(paimenetIsOk()){
			ticket.setCaisseSession(CaisseInterface.CAISSE_SESSION);
			ticket.setPaiedMoney(Double.parseDouble(lblPayedMoney.getText().replace(",", ".")));
			ticket.setRestMoney(Double.parseDouble(lblRestMoney.getText().replace(",", ".")));
			Component[] modePanels = paimentModebox.getComponents();
			for (Component modePanel : modePanels) {
				TicketHasModes ticketHasModes = new TicketHasModes();
				ticketHasModes.setTicket(ticket);
				ticketHasModes.setModeRegulation(((ModePaiment) modePanel).getModeRegulation());
				ticketHasModes.setMoney(((ModePaiment) modePanel).getMoney());
				
				ticket.getModes().add(ticketHasModes);
			}
			processSavingTicket(ticket);
			initPanel();
			caisseInterface.prepeareNewCommand();
		}
	}

	/**
	 * Saiving ticket process (Work in background)
	 * in case connection to database is not vaid the ticket will be saved in a local file
	 * @param ticket
	 */
	private void processSavingTicket(Ticket ticket) {
		saveWorker = new SwingWorker<Void, Void>(){

			@SuppressWarnings("unchecked")
			@Override
			protected Void doInBackground() throws Exception {
				if(Repository.connectionIsValid()){//---if connection is valid
					ticketRepository.create(ticket);
					
				}else{//---if not save the ticket in a file
					Services.logConsole("reading file...", AppConst.DEBUG_ON);
					FileInputStream fileInputStream = new FileInputStream(AppConst.NOT_SAVED_TICKETS_FILE);
					System.out.println("fileInputStream.available(): "+fileInputStream.available());
					List<Ticket> tickets;
					if(fileInputStream.available() > 0){
						ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
						tickets = (List<Ticket>) objectInputStream.readObject();
						objectInputStream.close();
					}else{
						tickets = new ArrayList<Ticket>();
					}
					tickets.add(ticket);
					Services.logConsole("Listing tickets...", AppConst.DEBUG_ON);
					for (Ticket t : tickets) {
						Services.logConsole(t.toString(), AppConst.DEBUG_ON);
					}				
					
					FileOutputStream fileOutputStream = new FileOutputStream(AppConst.NOT_SAVED_TICKETS_FILE);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(tickets);
					objectOutputStream.close();
				}
				
				return null;
			}
			
		};
		saveWorker.execute();
	}

	private void initPanel() {
		ticket = null;
		paimentModebox.removeAll();
		paimentModebox.revalidate();
	}

	/**
	 * Check if the paiment is good
	 * @return
	 */
	private boolean paimenetIsOk() {
		double rest = Double.parseDouble(lblRestMoney.getText().replace(",", "."));
		if(rest < 0){
			JOptionPane.showMessageDialog(caisseInterface, "La ticket n'est pas totallement payée !", "Paiement incomplet", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void clearMoney() {
		((JTextField) lastFocusedComponent).setText("");
	}

	/**
	 * 
	 * @param i
	 */
	private void appendMoney(double money) {
		double cMoney = (((JTextField) lastFocusedComponent).getText().equals("")) ? 0.0 :  Double.parseDouble(((JTextField) lastFocusedComponent).getText());
		double m = cMoney + money;
		((JTextField) lastFocusedComponent).setText(""+m);
	}

	/**
	 * When numpad is clicked
	 * 
	 * @param vkNumpad1
	 */
	private void numpadFired(int vkNumpad1) {
		lastFocusedComponent.requestFocusInWindow();
		numpadRobot.keyPress(vkNumpad1);
		numpadRobot.keyRelease(vkNumpad1);
	}

	/**
	 * Add new paiment mode
	 * 
	 * @param modeRegulation
	 */
	@SuppressWarnings("serial")
	private void addMode(ModeRegulation modeRegulation) {
		Services.logConsole("add mode fired", AppConst.DEBUG_ON);
		if (!modeExist(modeRegulation.getType())) {
			switch (modeRegulation.getType()) {
			// ----------------- ADD CASH MODE--------------------------
			case AppConst.CASH_MODE:
				CashPayPanel cashPayPanel = new CashPayPanel(paimentModebox, modeRegulation) {
					@Override
					protected void whenInputUpdatedDo() {
						calculatePaiedAndRestMoney();
					}

					@Override
					protected void whenMoneyfieldFocusedDo() {
						lastFocusedComponent = this.getMoneyFiled();
					}
				};
				Services.logConsole("add to panel", AppConst.DEBUG_ON);
				paimentModebox.add(cashPayPanel);
				paimentModebox.revalidate();
				Services.logConsole("added and calculating difference", AppConst.DEBUG_ON);
				double rest = Double.parseDouble(lblRestMoney.getText().replace(",", "."));
				cashPayPanel.setMoney((rest > 0) ? 0.0 : rest * -1);
				cashPayPanel.getMoneyFiled().requestFocusInWindow();
				cashPayPanel.getMoneyFiled().selectAll();
				Services.logConsole("done!", AppConst.DEBUG_ON);
				break;
			}
		}
	}

	/**
	 * Check if mode exist already
	 * 
	 * @param mode
	 * @return
	 */
	private boolean modeExist(int mode) {
		Component[] listModes = paimentModebox.getComponents();
		for (Component component : listModes) {
			if (((ModePaiment) component).getMode() == mode) {
				JOptionPane.showMessageDialog(caisseInterface,
						"Ce mode exist déjà !", "Ajout impossible",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	private void rollBackTicket() {
		caisseInterface.getBackToCaissFacade();
	}

	/**
	 * 
	 * @param ticket
	 */
	public void payTicket(Ticket ticket) {
		// if it's a new ticket add cash pay panel
		if (this.ticket == null) {
			this.ticket = ticket;
			lblPayedMoney.setText(AppConst.numberFormat.format(0));
			lblRestMoney.setText(AppConst.numberFormat.format(0 - ticket.getTotTicket()));
			ModeRegulationButton firstButton = (ModeRegulationButton) modeRegulationPanel.getComponents()[0];
			firstButton.doClick();
		}
		lblTotTicket.setText(AppConst.numberFormat.format(ticket.getTotTicket()));
		this.ticket = ticket;
		calculatePaiedAndRestMoney();

	}

	/**
	 * Update paied and rest money
	 */
	protected void calculatePaiedAndRestMoney() {
		double paiedMoney = 0.0;
		Component[] listModes = paimentModebox.getComponents();
		if (listModes.length != 0) {
			for (Component component : listModes) {
				paiedMoney += ((ModePaiment) component).getMoney();
			}
		}
		lblPayedMoney.setText(AppConst.numberFormat.format(paiedMoney));
		lblRestMoney.setText(AppConst.numberFormat.format(paiedMoney - ticket.getTotTicket()));
	}

	public void initRegulationModes() {
		List<ModeRegulation> list = modeRegulationRepository.read();
		for (ModeRegulation modeRegulation : list) {
			ModeRegulationButton btnMode = new ModeRegulationButton(modeRegulation);
			btnMode.addActionListener(e -> addMode(modeRegulation));
			modeRegulationPanel.add(btnMode);
		}
		modeRegulationPanel.revalidate();
		
	}
}

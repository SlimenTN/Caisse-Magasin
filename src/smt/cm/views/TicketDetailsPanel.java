package smt.cm.views;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import smt.cm.comps.ChangeProdutValuesWindow;
import smt.cm.comps.DeleteProductRenderer;
import smt.cm.comps.NumpadButton;
import smt.cm.comps.SToggleButton;
import smt.cm.comps.TicketRenderer;
import smt.cm.comps.TicketRendererObject;
import smt.cm.controllers.OutgoCaisseRepository;
import smt.cm.entities.OutgoCaisse;
import smt.cm.entities.Product;
import smt.cm.entities.Ticket;
import smt.cm.entities.TicketLine;
import smt.cm.util.AppConst;
import smt.renderer.MoneyRenderer;
import smt.services.Services;
import smt.services.TabModel;

public class TicketDetailsPanel extends JPanel {
	private static final long serialVersionUID = -349926267404440246L;

	private final String LINES_TICKET_PANEL = "LINES_TICKET_PANEL";
	private final String OUTGO_PANEL = "OUTGO_PANEL";
	

	private final int QTE = 0;
	private final int REM = 1;
	private final int PRICE = 2;
	private int selectedTarget;

	private JPanel totTicketPanel;
	private JLabel lblTotTicket;
	private JPanel numPadPanel;
	private NumpadButton btnValid;
	private NumpadButton btnEnAttente;
	private NumpadButton btnAnnuler_1;
	private NumpadButton btnDpense;
	private JScrollPane scrollPane_1;
	private JTable ticketTable;
	private TabModel model;
	private String[] header = new String[] { "Produit", "Prix", "Action" };
	private final int productCol = 0;
	private final int priceCol = 1;
	private final int deleteCol = 2;
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
	private NumpadButton numPadBack;
	private NumpadButton numPadPoint;
	private SToggleButton toggleQte;
	private SToggleButton toggleRemise;
	private SToggleButton togglePrice;
	private ButtonGroup toggleGroup = new ButtonGroup();
	private NumpadButton btnEnt;
	
	private JPanel cardPanel;
	private CardLayout cardLayout;

	private CaisseInterface caisseInterface;
	private ChangeProdutValuesWindow produtValuesWindow;
	private Robot numpadRobot;
	private JPanel outgoPanel;
	private JLabel lblNewLabel;
	private JTextField outgoField;
	private NumpadButton btnCancelOutgo;
	private JPanel panel_1;
	
	private OutgoCaisseRepository outgoCaisseRepository = new OutgoCaisseRepository();

	public TicketDetailsPanel(CaisseInterface caisseInterface) {
		this.caisseInterface = caisseInterface;

		setPreferredSize(new Dimension(500, 624));
		setLayout(new BorderLayout(0, 0));

		totTicketPanel = new JPanel();
		totTicketPanel.setBackground(Color.BLACK);
		totTicketPanel.setPreferredSize(new Dimension(10, 80));
		add(totTicketPanel, BorderLayout.NORTH);

		lblTotTicket = new JLabel("0,000 DT");
		lblTotTicket.setForeground(Color.CYAN);
		lblTotTicket.setFont(new Font("Modern No. 20", Font.BOLD, 60));
		totTicketPanel.add(lblTotTicket);

		numPadPanel = new JPanel();
		numPadPanel.setBackground(Color.WHITE);
		numPadPanel.setPreferredSize(new Dimension(10, 300));
		add(numPadPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_numPadPanel = new GridBagLayout();
		gbl_numPadPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_numPadPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_numPadPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_numPadPanel.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		numPadPanel.setLayout(gbl_numPadPanel);

		numPad1 = new NumpadButton("1");
		numPad1.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD1));
		numPad1.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad1 = new GridBagConstraints();
		gbc_numPad1.insets = new Insets(0, 0, 5, 5);
		gbc_numPad1.gridx = 0;
		gbc_numPad1.gridy = 0;
		numPadPanel.add(numPad1, gbc_numPad1);

		numPad2 = new NumpadButton("2");
		numPad2.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD2));
		numPad2.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad2 = new GridBagConstraints();
		gbc_numPad2.insets = new Insets(0, 0, 5, 5);
		gbc_numPad2.gridx = 1;
		gbc_numPad2.gridy = 0;
		numPadPanel.add(numPad2, gbc_numPad2);

		numPad3 = new NumpadButton("3");
		numPad3.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD3));
		numPad3.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad3 = new GridBagConstraints();
		gbc_numPad3.insets = new Insets(0, 0, 5, 5);
		gbc_numPad3.gridx = 2;
		gbc_numPad3.gridy = 0;
		numPadPanel.add(numPad3, gbc_numPad3);

		toggleQte = new SToggleButton("Qte");
		toggleQte.addActionListener(e -> setTargetValue(QTE));
		toggleQte.setFont(new Font("Tahoma", Font.BOLD, 20));
		toggleGroup.add(toggleQte);
		toggleQte.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_toggleQte = new GridBagConstraints();
		gbc_toggleQte.insets = new Insets(0, 0, 5, 5);
		gbc_toggleQte.gridx = 3;
		gbc_toggleQte.gridy = 0;
		numPadPanel.add(toggleQte, gbc_toggleQte);

		toggleRemise = new SToggleButton("Rem");
		toggleRemise.addActionListener(e -> setTargetValue(REM));
		toggleRemise.setFont(new Font("Tahoma", Font.BOLD, 20));
		toggleGroup.add(toggleRemise);
		toggleRemise.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_toggleRemise = new GridBagConstraints();
		gbc_toggleRemise.insets = new Insets(0, 0, 5, 5);
		gbc_toggleRemise.gridx = 3;
		gbc_toggleRemise.gridy = 1;
		numPadPanel.add(toggleRemise, gbc_toggleRemise);

		togglePrice = new SToggleButton("Prix");
		togglePrice.addActionListener(e -> setTargetValue(PRICE));
		togglePrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		toggleGroup.add(togglePrice);
		togglePrice.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_togglePrice = new GridBagConstraints();
		gbc_togglePrice.insets = new Insets(0, 0, 5, 5);
		gbc_togglePrice.gridx = 3;
		gbc_togglePrice.gridy = 2;
		numPadPanel.add(togglePrice, gbc_togglePrice);

		btnValid = new NumpadButton("Valider");
		btnValid.setPreferredSize(new Dimension(130, 70));
		btnValid.addActionListener(e -> validateTicket());
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridwidth = 2;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 6;
		gbc_btnNewButton_2.gridy = 0;
		numPadPanel.add(btnValid, gbc_btnNewButton_2);

		numPad4 = new NumpadButton("4");
		numPad4.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD4));
		numPad4.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad4 = new GridBagConstraints();
		gbc_numPad4.insets = new Insets(0, 0, 5, 5);
		gbc_numPad4.gridx = 0;
		gbc_numPad4.gridy = 1;
		numPadPanel.add(numPad4, gbc_numPad4);

		numPad5 = new NumpadButton("5");
		numPad5.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD5));
		numPad5.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad5 = new GridBagConstraints();
		gbc_numPad5.insets = new Insets(0, 0, 5, 5);
		gbc_numPad5.gridx = 1;
		gbc_numPad5.gridy = 1;
		numPadPanel.add(numPad5, gbc_numPad5);

		numPad6 = new NumpadButton("6");
		numPad6.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD6));
		numPad6.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad6 = new GridBagConstraints();
		gbc_numPad6.insets = new Insets(0, 0, 5, 5);
		gbc_numPad6.gridx = 2;
		gbc_numPad6.gridy = 1;
		numPadPanel.add(numPad6, gbc_numPad6);

		btnEnAttente = new NumpadButton("En attente");
		btnEnAttente.addActionListener(e -> setWaitingTicket());
		btnEnAttente.setPreferredSize(new Dimension(130, 70));
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.gridwidth = 2;
		gbc_btnAnnuler.insets = new Insets(0, 0, 5, 0);
		gbc_btnAnnuler.gridx = 6;
		gbc_btnAnnuler.gridy = 1;
		numPadPanel.add(btnEnAttente, gbc_btnAnnuler);

		numPad7 = new NumpadButton("7");
		numPad7.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD7));
		numPad7.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad7 = new GridBagConstraints();
		gbc_numPad7.insets = new Insets(0, 0, 5, 5);
		gbc_numPad7.gridx = 0;
		gbc_numPad7.gridy = 2;
		numPadPanel.add(numPad7, gbc_numPad7);

		numPad8 = new NumpadButton("8");
		numPad8.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD8));
		numPad8.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad8 = new GridBagConstraints();
		gbc_numPad8.insets = new Insets(0, 0, 5, 5);
		gbc_numPad8.gridx = 1;
		gbc_numPad8.gridy = 2;
		numPadPanel.add(numPad8, gbc_numPad8);

		numPad9 = new NumpadButton("9");
		numPad9.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD9));
		numPad9.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad9 = new GridBagConstraints();
		gbc_numPad9.insets = new Insets(0, 0, 5, 5);
		gbc_numPad9.gridx = 2;
		gbc_numPad9.gridy = 2;
		numPadPanel.add(numPad9, gbc_numPad9);

		btnAnnuler_1 = new NumpadButton("Annuler");
		btnAnnuler_1.addActionListener(e -> cancelTicket());
		btnAnnuler_1.setPreferredSize(new Dimension(130, 70));
		GridBagConstraints gbc_btnAnnuler_1 = new GridBagConstraints();
		gbc_btnAnnuler_1.gridwidth = 2;
		gbc_btnAnnuler_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnAnnuler_1.gridx = 6;
		gbc_btnAnnuler_1.gridy = 2;
		numPadPanel.add(btnAnnuler_1, gbc_btnAnnuler_1);

		numPadBack = new NumpadButton("->");
		numPadBack.addActionListener(e -> nupmpadFired(KeyEvent.VK_BACK_SPACE));
		numPadBack.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPadBack = new GridBagConstraints();
		gbc_numPadBack.insets = new Insets(0, 0, 0, 5);
		gbc_numPadBack.gridx = 0;
		gbc_numPadBack.gridy = 3;
		numPadPanel.add(numPadBack, gbc_numPadBack);

		numPad0 = new NumpadButton("0");
		numPad0.addActionListener(e -> nupmpadFired(KeyEvent.VK_NUMPAD0));
		numPad0.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPad0 = new GridBagConstraints();
		gbc_numPad0.insets = new Insets(0, 0, 0, 5);
		gbc_numPad0.gridx = 1;
		gbc_numPad0.gridy = 3;
		numPadPanel.add(numPad0, gbc_numPad0);

		numPadPoint = new NumpadButton(".");
		numPadPoint.addActionListener(e -> nupmpadFired(KeyEvent.VK_DECIMAL));
		numPadPoint.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_numPadPoint = new GridBagConstraints();
		gbc_numPadPoint.insets = new Insets(0, 0, 0, 5);
		gbc_numPadPoint.gridx = 2;
		gbc_numPadPoint.gridy = 3;
		numPadPanel.add(numPadPoint, gbc_numPadPoint);

		btnEnt = new NumpadButton("Ent");
		btnEnt.addActionListener(e -> nupmpadFired(KeyEvent.VK_ENTER));
		btnEnt.setPreferredSize(new Dimension(70, 70));
		GridBagConstraints gbc_btnEnt = new GridBagConstraints();
		gbc_btnEnt.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnt.gridx = 3;
		gbc_btnEnt.gridy = 3;
		numPadPanel.add(btnEnt, gbc_btnEnt);

		btnDpense = new NumpadButton("D\u00E9pense");
		btnDpense.addActionListener(e -> createOutgoCaisse());
		btnDpense.setPreferredSize(new Dimension(130, 70));
		GridBagConstraints gbc_btnDpense = new GridBagConstraints();
		gbc_btnDpense.gridwidth = 2;
		gbc_btnDpense.gridx = 6;
		gbc_btnDpense.gridy = 3;
		numPadPanel.add(btnDpense, gbc_btnDpense);

		cardPanel = new JPanel();
		cardLayout = new CardLayout(0, 0);
		cardPanel.setLayout(cardLayout);
		add(cardPanel, BorderLayout.CENTER);
		
		scrollPane_1 = new JScrollPane();
		cardPanel.add(scrollPane_1, LINES_TICKET_PANEL);

		ticketTable = new JTable();
		model = new TabModel(header) {
			private static final long serialVersionUID = 5772043787813614974L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == deleteCol)
					return true;
				return false;
			};
		};
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				calculTotTicket();
			}
		});
		ticketTable.setModel(model);
		ticketTable.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				TicketDetailsPanel.this.caisseInterface.setLastFocusedComponent(ticketTable);
			}
		});
		scrollPane_1.setViewportView(ticketTable);
		
		outgoPanel = new JPanel();
		outgoPanel.setBorder(new MatteBorder(0, 1, 1, 0, (Color) new Color(128, 128, 128)));
		outgoPanel.setBackground(Color.WHITE);
		cardPanel.add(outgoPanel, OUTGO_PANEL);
		outgoPanel.setLayout(new BoxLayout(outgoPanel, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(32767, 50));
		panel_1.setOpaque(false);
		outgoPanel.add(panel_1);
		
		lblNewLabel = new JLabel("Tapper le montant de d\u00E9pense");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		outgoPanel.add(lblNewLabel);
		
		outgoField = new JTextField();
		outgoField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		outgoField.setHorizontalAlignment(SwingConstants.CENTER);
		outgoField.setMaximumSize(new Dimension(250, 40));
		outgoField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				TicketDetailsPanel.this.caisseInterface.setLastFocusedComponent(outgoField);
			}
		});
		outgoField.addActionListener(e -> confirmOutgo());
		outgoPanel.add(outgoField);
		
		btnCancelOutgo = new NumpadButton((String) null);
		btnCancelOutgo.addActionListener(e -> clearOutgo());
		btnCancelOutgo.setMaximumSize(new Dimension(250, 40));
		btnCancelOutgo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCancelOutgo.setText("Annuler");
		outgoPanel.add(btnCancelOutgo);

		produtValuesWindow = new ChangeProdutValuesWindow(caisseInterface);

		try {
			numpadRobot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		prepeareTableRender();
		toggleQte.doClick();
	}

	private void confirmOutgo() {
		OutgoCaisse outgoCaisse = new OutgoCaisse();
		outgoCaisse.setMoney(Double.parseDouble(outgoField.getText()));
		outgoCaisse.setCaisseSession(CaisseInterface.CAISSE_SESSION);
		outgoCaisseRepository.create(outgoCaisse);
		JOptionPane.showMessageDialog(caisseInterface, "Le montant de dépense à été bien enregistrer.", "Dépense caisse", JOptionPane.INFORMATION_MESSAGE);
		clearOutgo();
	}

	private void clearOutgo() {
		outgoField.setText("");
		cardLayout.show(cardPanel, LINES_TICKET_PANEL);
		caisseInterface.focusBarcodeField();
	}

	private void createOutgoCaisse() {
		cardLayout.show(cardPanel, OUTGO_PANEL);
		outgoField.requestFocusInWindow();
	}

	private void setTargetValue(int v) {
		selectedTarget = v;
		String s = "";
		switch (v) {
		case QTE:
			s = "Changer Quantité";
			break;

		case REM:
			s = "Ajouter Remise";
			break;

		case PRICE:
			s = "Modifier Prix";
			break;
		}
		produtValuesWindow.setTargetValueText(s);
	}

	/**
	 * Cancle command and rest panel
	 * 
	 * @return
	 */
	private void cancelTicket() {
		Ticket ticket = getCurrentTicket();
		if (ticket != null) {
			int res = JOptionPane.showConfirmDialog(caisseInterface,
					"Confonfirmez-vous l'annulation de cette ticket ?",
					"Annulation ticket", JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.YES_OPTION)
				caisseInterface.prepeareNewCommand();
		} else {
			JOptionPane.showMessageDialog(caisseInterface,
					"Pas de ticket à annuler !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Set ticket in waiting list
	 * 
	 * @return
	 */
	private void setWaitingTicket() {
		Ticket ticket = getCurrentTicket();
		if (ticket != null) {
			caisseInterface.addTicketToWaitingList(getCurrentTicket());
			caisseInterface.prepeareNewCommand();
		} else {
			JOptionPane.showMessageDialog(caisseInterface,
					"Pas de ticket pour mettre en attente !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Pay ticket
	 * 
	 * @return
	 */
	private void validateTicket() {
		Ticket ticket = getCurrentTicket();
		if (ticket != null)
			caisseInterface.sendTicketForPaiement(ticket);
		else
			JOptionPane.showMessageDialog(caisseInterface,
					"Pas de ticket à solder !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Set data in ticket object
	 * 
	 * @return
	 */
	private Ticket getCurrentTicket() {
		int nbLines = model.getRowCount();
		Ticket ticket = null;
		if (nbLines != 0) {
			ticket = new Ticket();
			ticket.setCode(generateBarcodeEAN13());
			ticket.setDateTicket(new Date());

			ticket.setTotTicket(Double.parseDouble(lblTotTicket.getText()
					.replace(" DT", "").replace(",", ".")));
			ticket.setTotRemise(0.0);

			for (int i = 0; i < nbLines; i++) {
				TicketLine line = new TicketLine();
				TicketRendererObject ticketRendererObject = (TicketRendererObject) model.getValueAt(i, productCol);
				double linePrice = (double) model.getValueAt(i, priceCol);
				line.setProduct(ticketRendererObject.getProduct());
				line.setTicket(ticket);
				line.setLinePrice(linePrice);
				line.setProductPrice(ticketRendererObject.getProduct().getSalePrice());
				line.setQuantity(ticketRendererObject.getQuantity());
				line.setRemise(ticketRendererObject.getRemise());

				ticket.getTicketLines().add(line);
			}
		}
		return ticket;
	}

	/**
	 * Generate EAN-13 barecode
	 * 
	 * @return
	 */
	private String generateBarcodeEAN13() {
		return "" + (new Date().getTime());
	}

	/**
	 * Do when numpad pressed
	 * 
	 * @param string
	 * @return
	 */
	private void nupmpadFired(int vkNumpad) {
		Services.logConsole(caisseInterface.getLastFocusedComponent().getClass().getName(), AppConst.DEBUG_ON);
		if(caisseInterface.getLastFocusedComponent() instanceof JTable){			
			if (model.getRowCount() != 0) {
				if (!produtValuesWindow.isVisible()) {
					produtValuesWindow.getValueField().clearText();
					displayValueSwitcher();

				}
				produtValuesWindow.getValueField().requestFocus();
				numpadRobot.keyPress(vkNumpad);
				numpadRobot.keyRelease(vkNumpad);

				if (vkNumpad == KeyEvent.VK_ENTER) {
					double value = Double.parseDouble(produtValuesWindow
							.getValueField().getText());
					int sr = ticketTable.getSelectedRow();
					TicketRendererObject ticketRendererObject = (TicketRendererObject) model
							.getValueAt(sr, productCol);
					double newTotRow = 0;
					switch (selectedTarget) {
					case QTE:
						ticketRendererObject.setQuantity(value);
						break;

					case REM:
						ticketRendererObject.setRemise(value);
						break;

					case PRICE:
						ticketRendererObject.getProduct().setSalePrice(value);
						break;
					}
					newTotRow = getTotTicketRow(ticketRendererObject);
					model.setValueAt(ticketRendererObject, sr, productCol);
					model.setValueAt(newTotRow, sr, priceCol);
					produtValuesWindow.setVisible(false);

				}
			}
		}else{
			caisseInterface.getLastFocusedComponent().requestFocusInWindow();
			numpadRobot.keyPress(vkNumpad);
			numpadRobot.keyRelease(vkNumpad);
		}

	}

	private void displayValueSwitcher() {
		Point tablePoint = ticketTable.getLocationOnScreen();
		produtValuesWindow.setLocation((int) tablePoint.getX() - 200,
				(int) tablePoint.getY());
		System.out.println((int) tablePoint.getX() - 200 + ","
				+ (int) tablePoint.getY());
		produtValuesWindow.setVisible(true);
	}

	private void prepeareTableRender() {
		ticketTable.setRowHeight(60);
		ticketTable.setTableHeader(null);
		Services.setWidthAsPercentages(ticketTable, 0.7, 0.2, 0.1);
		ticketTable.setIntercellSpacing(new Dimension(0, 1));
		ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TicketRenderer renderer = new TicketRenderer();
		MoneyRenderer moneyRenderer = new MoneyRenderer(new Font("Tahoma",
				Font.BOLD, 15));
		DeleteProductRenderer deleteProductRenderer = new DeleteProductRenderer();
		ticketTable.getColumnModel().getColumn(productCol)
				.setCellRenderer(renderer);
		ticketTable.getColumnModel().getColumn(priceCol)
				.setCellRenderer(moneyRenderer);
		ticketTable.getColumnModel().getColumn(deleteCol)
				.setCellEditor(deleteProductRenderer);
		ticketTable.getColumnModel().getColumn(deleteCol)
				.setCellRenderer(deleteProductRenderer);
	}

	/**
	 * 
	 * @param product
	 */
	public void addProductToTicket(Product product) {
		int pos = productPosition(product);
		if (pos != -1) {
			increaseQuantity(product, pos);
			ticketTable.setRowSelectionInterval(pos, pos);
		} else {
			addNewProduct(product);
			ticketTable.setRowSelectionInterval(0, 0);
		}
		// calculTotTicket();

	}

	/**
	 * Calcul tot ticket
	 */
	private void calculTotTicket() {
		int nbRows = model.getRowCount();
		double tot = 0.0;
		for (int i = 0; i < nbRows; i++) {
			Services.logConsole("price: " + model.getValueAt(i, priceCol), AppConst.DEBUG_ON);
			tot += (double) model.getValueAt(i, priceCol);
		}
		Services.logConsole("tot: " + tot, AppConst.DEBUG_ON);
		tot = new BigDecimal(tot).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		lblTotTicket.setText(AppConst.numberFormat.format(tot) + " DT");
	}

	/**
	 * Check if product already added to ticket
	 * 
	 * @param product
	 * @return int
	 */
	private int productPosition(Product product) {
		int nbRows = model.getRowCount();
		for (int i = 0; i < nbRows; i++) {
			Product prd = ((TicketRendererObject) model.getValueAt(i,
					productCol)).getProduct();
			if (prd.equals(product))
				return i;
		}
		return -1;
	}

	/**
	 * Increase product's price
	 * 
	 * @param product
	 * @param row
	 */
	private void increaseQuantity(Product product, int row) {
		TicketRendererObject ticketRendererObject = (TicketRendererObject) model.getValueAt(row, productCol);
		ticketRendererObject.increaseQuantity();
		double price = getTotTicketRow(ticketRendererObject);	
		model.setValueAt(ticketRendererObject, row, productCol);
		model.setValueAt(price, row, priceCol);
	}
	
	/**
	 * Calcul totale of specific row in ticket
	 * @param ticketRendererObject
	 * @return
	 */
	private double getTotTicketRow(TicketRendererObject ticketRendererObject){
		double totRow = 0;
		double remise = ticketRendererObject.getRemise();		
		double remiseValue = 0;
		if(remise != 0){
			remiseValue = (remise / 100) * (ticketRendererObject.getProduct().getSalePrice() * ticketRendererObject.getQuantity());
		}
		totRow = (ticketRendererObject.getProduct().getSalePrice() * ticketRendererObject.getQuantity()) - remiseValue;
		return totRow;
	}

	/**
	 * Add new product to ticket if not exist
	 * 
	 * @param product
	 */
	private void addNewProduct(Product product) {
		List<Object> row = new ArrayList<Object>();
		row.add(new TicketRendererObject(product, 1, 0));
		row.add(product.getSalePrice());
		row.add(null);

		model.addRow(row);
	}

	public void initPanel() {
		model.resetTable();
		lblTotTicket.setText("0,000 DT");
		toggleQte.doClick();
	}

	/**
	 * Reset waiting ticket
	 * @param selectedTicket
	 */
	public void resetWaitingTicket(Ticket selectedTicket) {
		Set<TicketLine> ticketLines = selectedTicket.getTicketLines();
		
		List<Object> rows = new ArrayList<Object>();
		for (TicketLine ticketLine : ticketLines) {
			List<Object> row = new ArrayList<Object>();
			row.add(new TicketRendererObject(ticketLine.getProduct(), ticketLine.getQuantity(), ticketLine.getRemise()));
			row.add(ticketLine.getLinePrice());
			row.add(null);
			
			rows.add(row);
		}
		model.reBuildTabModel(rows);
	}

}

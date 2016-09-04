package smt.cm.comps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import smt.cm.entities.ModeRegulation;
import smt.cm.interfaces.ModePaiment;
import smt.cm.util.AppConst;
import smt.services.Services;

public class CashPayPanel extends JPanel implements ModePaiment{
	
	private static final long serialVersionUID = 6519209464854900413L;
	private JTextField moneyFiled;
	private Box container;
	private ModeRegulation modeRegulation;
	
	public CashPayPanel(Box paimentModebox, ModeRegulation modeRegulation) {
		container = paimentModebox;
		this.modeRegulation = modeRegulation;
		setBorder(new LineBorder(Color.GRAY));
		setPreferredSize(new Dimension(450, 100));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Esp\u00E8ce");
		lblNewLabel.setForeground(new Color(255, 255, 224));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		NumpadButton nmpdbtnFermer = new NumpadButton((String) null);
		nmpdbtnFermer.addActionListener(e -> removeThisFromContainer());
		nmpdbtnFermer.setPreferredSize(new Dimension(70, 50));
		nmpdbtnFermer.setText("Supprimer");
		panel.add(nmpdbtnFermer);
		
		moneyFiled = new JTextField();
		moneyFiled.setFont(new Font("Tahoma", Font.PLAIN, 30));
		moneyFiled.setHorizontalAlignment(SwingConstants.CENTER);
		moneyFiled.setText("54,000");
		moneyFiled.setBorder(null);
		add(moneyFiled, BorderLayout.CENTER);

		moneyFiled.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				whenInputUpdatedDo();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				whenInputUpdatedDo();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		moneyFiled.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
//				moneyFiled.selectAll();
				whenMoneyfieldFocusedDo();
			};
		});
	}

	/**
	 * 
	 */
	protected void whenMoneyfieldFocusedDo() {}

	/**
	 * 
	 */
	private void removeThisFromContainer() {
		Services.logConsole(getParent().getName(), AppConst.DEBUG_ON);
		moneyFiled.setText("");
		container.remove(this);
		container.revalidate();
	}

	/**
	 * Override this methode for custom function when field updated
	 */
	protected void whenInputUpdatedDo() {}

	public void setMoney(double totTicket) {
		moneyFiled.setText(""+totTicket);
		moneyFiled.selectAll();
	}

	@Override
	public double getMoney() {
		return (moneyFiled.getText().equals("")) ? 0.0 : Double.parseDouble(moneyFiled.getText().replace(",", "."));
	}

	@Override
	public int getMode() {
		return modeRegulation.getType();
	}

	public JTextField getMoneyFiled() {
		return moneyFiled;
	}

	@Override
	public ModeRegulation getModeRegulation() {
		return modeRegulation;
	}
	
	

}

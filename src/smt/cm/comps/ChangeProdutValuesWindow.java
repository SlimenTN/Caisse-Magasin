package smt.cm.comps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

import smt.cm.views.CaisseInterface;
import smt.textfield.STextField;

public class ChangeProdutValuesWindow extends JWindow{
	
	private static final long serialVersionUID = 3590792138807505545L;
	private JLabel lblTargetValue;
	private STextField valueField;
	public ChangeProdutValuesWindow(CaisseInterface parent) {
		super(parent);
		setSize(new Dimension(200, 60));
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblTargetValue = new JLabel("Quantit\u00E9");
		lblTargetValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTargetValue.setBounds(10, 11, 123, 14);
		panel.add(lblTargetValue);
		
		valueField = new STextField();
		valueField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		valueField.setBounds(10, 26, 180, 23);
		panel.add(valueField);
		
	}
	
	public STextField getValueField() {
		return valueField;
	}
	
	public void setTargetValueText(String txt){
		lblTargetValue.setText(txt);
	}
}

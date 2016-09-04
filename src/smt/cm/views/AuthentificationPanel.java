package smt.cm.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import smt.cm.comps.NumpadButton;

public class AuthentificationPanel extends JPanel {
	private static final long serialVersionUID = 6058877931621557265L;
	private JLabel lblNewLabel;
	private JTextField fondField;
	private JPasswordField passwordField;
	private JPanel panel;
	private CaisseInterface caisseInterface;
	private NumpadButton btnAuthentification;

	/**
	 * Create the panel.
	 */
	public AuthentificationPanel(CaisseInterface caisseInterface) {
		this.caisseInterface = caisseInterface;
		setBorder(null);
		setBackground(new Color(102, 0, 102));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(32767, 200));
		add(panel);
		
		lblNewLabel = new JLabel("Authentification");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel);
		
		fondField = new JTextField();
		fondField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		fondField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		fondField.setHorizontalAlignment(SwingConstants.CENTER);
		fondField.setText("150");
		fondField.setMaximumSize(new Dimension(300, 40));
		add(fondField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setText("123456789");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		passwordField.setMaximumSize(new Dimension(300, 40));
		add(passwordField);
		
		btnAuthentification = new NumpadButton((String) null);
		btnAuthentification.addActionListener(e -> checkAuthentification());
		btnAuthentification.setMaximumSize(new Dimension(300, 40));
		btnAuthentification.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAuthentification.setText("Entrer");
		add(btnAuthentification);

	}

	/**
	 * 
	 */
	private void checkAuthentification() {		
		caisseInterface.startAuthentification(Double.parseDouble(fondField.getText()), new String(passwordField.getPassword()));
	}
}

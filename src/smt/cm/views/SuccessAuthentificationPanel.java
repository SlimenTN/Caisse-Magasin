package smt.cm.views;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

public class SuccessAuthentificationPanel extends JPanel{
	private static final long serialVersionUID = 1969929404116880005L;
	private JLabel lblEmployeeName;

	public SuccessAuthentificationPanel(CaisseInterface caisseInterface) {
		setBackground(new Color(51, 102, 102));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(32767, 200));
		add(panel);
		
		lblEmployeeName = new JLabel("Bonjour Arnaout Slimen :)");
		lblEmployeeName.setFont(new Font("Century Gothic", Font.PLAIN, 65));
		lblEmployeeName.setForeground(new Color(255, 255, 255));
		lblEmployeeName.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblEmployeeName);
		
		JLabel lblNewLabel_1 = new JLabel("<html><center>Pr\u00E9paration de la caisse en cours<br>Veuillez patienter ...</center></html>");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 40));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel_1);
	}

	public void setEmployeeName(String name) {
		lblEmployeeName.setText("Bonjour "+name+" :)");

		
	}
}

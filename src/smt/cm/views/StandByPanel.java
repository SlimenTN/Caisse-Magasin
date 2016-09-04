package smt.cm.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import smt.cm.comps.NumpadButton;
import smt.cm.util.AppConst;
import smt.services.Services;

public class StandByPanel extends JPanel{
	private static final long serialVersionUID = -4452314662107548501L;
	private JPasswordField passwordField;
	private JLabel lblEMployeeName;
	private NumpadButton accesBtn;
	private CaisseInterface caisseInterface;

	public StandByPanel(CaisseInterface caisseInterface) {
		this.caisseInterface = caisseInterface;
		setBackground(new Color(102, 0, 204));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(32767, 200));
		add(panel);
		
		lblEMployeeName = new JLabel("Arnaout Slimen");
		lblEMployeeName.setForeground(new Color(255, 255, 255));
		lblEMployeeName.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblEMployeeName.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblEMployeeName);
		
		JLabel lblNewLabel_1 = new JLabel("<html><center>La caisse est en veille<br>Veuillez tapper votre mot de passe pour reconnecter</center></html>");
		lblNewLabel_1.setBorder(new EmptyBorder(0, 0, 10, 0));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setMaximumSize(new Dimension(300, 40));
		add(passwordField);
		passwordField.setColumns(10);
		
		accesBtn = new NumpadButton((String) null);
		accesBtn.addActionListener(e -> checkUser());
		accesBtn.setText("Entrer");
		accesBtn.setMaximumSize(new Dimension(300, 40));
		accesBtn.setAlignmentX(0.5f);
		add(accesBtn);
		
	}

	private void checkUser() {
		String password = new String(passwordField.getPassword());
		Services.logConsole("pw: "+password+" currrent user pw: "+CaisseInterface.CAISSE_SESSION.getEmployee().getCaissePassword(), AppConst.DEBUG_ON);
		if(password == null || !CaisseInterface.CAISSE_SESSION.getEmployee().getCaissePassword().equals(password)){
			JOptionPane.showMessageDialog(caisseInterface, "Cette mot de passe ne correspond pas à l'utilsateur courant !", "Accès refusé", JOptionPane.ERROR_MESSAGE);
		}else{
			caisseInterface.openCaisse();
		}
	}

	public void setUserName(String name) {
		lblEMployeeName.setText(name);
	}
	
}

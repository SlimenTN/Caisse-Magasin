package smt.cm.views;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import smt.cm.comps.NumpadButton;
import smt.cm.controllers.Repository;
import smt.cm.controllers.TicketRepository;
import smt.cm.entities.Ticket;
import smt.cm.util.AppConst;
import smt.services.Services;

public class HeaderCaissPanel extends JPanel{
	private static final long serialVersionUID = 696843670637642098L;
	private NumpadButton btnWaitingTickets;
	private NumpadButton btnSleepCaisse;
	private NumpadButton btnCloseCaisse;
	private JLabel lblUserName;
	private CaisseInterface caisseInterface;
	private NumpadButton connectionStatButton;
	private SwingWorker<Void, Void> saveWorker;

	public HeaderCaissPanel(CaisseInterface caisseInterface) {
		this.caisseInterface = caisseInterface;
		setBackground(Color.WHITE);
		
		btnCloseCaisse = new NumpadButton("X");
		btnCloseCaisse.addActionListener(e -> closeCaisse());		
		btnSleepCaisse = new NumpadButton((String) null);
		btnSleepCaisse.addActionListener(e -> setCaisseOnStandByMode());
		btnSleepCaisse.setText("O");
		
		btnWaitingTickets = new NumpadButton("En attente: 0");
		btnWaitingTickets.addActionListener(e -> this.caisseInterface.displayWaitingTickets());
		
		lblUserName = new JLabel("Arnaout Slimen");
		lblUserName.setIcon(new ImageIcon(HeaderCaissPanel.class.getResource("/smt/cm/img/contact_blue.png")));
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserName.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		
		connectionStatButton = new NumpadButton("");
		connectionStatButton.addActionListener(e -> verifConnection());
//		connectionStatButton.setBackground(Color.GREEN);
//		connectionStatButton.setIcon(new ImageIcon(HeaderCaissPanel.class.getResource("/smt/cm/img/connect.png")));
		connectionStatButton.setText("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnWaitingTickets, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
					.addComponent(lblUserName, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(connectionStatButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSleepCaisse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCloseCaisse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnWaitingTickets, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnCloseCaisse, 0, 0, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSleepCaisse, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
								.addComponent(lblUserName)))
						.addComponent(connectionStatButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);
		initConnectionCheckerDeamon();
	}

	private void closeCaisse() {
		caisseInterface.closeCaisse();
	}

	private void setCaisseOnStandByMode() {
		caisseInterface.setStandByMode();
	}

	/**
	 * Check if connection is valid every 5 seconds
	 * 
	 */
	private void initConnectionCheckerDeamon() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				verifConnection();
			}
		}, 0, 5000);
	}

	/**
	 * Verification function
	 */
	private void  verifConnection() {
		Services.logConsole("checking...", AppConst.DEBUG_ON);
		if(Repository.connectionIsValid()){
			Services.logConsole("connection valid", AppConst.DEBUG_ON);
			connectionStatButton.setBackground(Color.GREEN);
			connectionStatButton.setIcon(new ImageIcon(HeaderCaissPanel.class.getResource("/smt/cm/img/connect.png")));
			checkLocalTicketsFile();
		}else{
			Services.logConsole("connection not valid", AppConst.DEBUG_ON);
			connectionStatButton.setBackground(Color.RED);
			connectionStatButton.setIcon(new ImageIcon(HeaderCaissPanel.class.getResource("/smt/cm/img/disconnect.png")));
		}
	}

	private void checkLocalTicketsFile() {
		saveWorker = new SwingWorker<Void, Void>(){

			@SuppressWarnings("unchecked")
			@Override
			protected Void doInBackground() throws Exception {
				Services.logConsole("reading file...", AppConst.DEBUG_ON);
				FileInputStream fileInputStream = new FileInputStream(AppConst.NOT_SAVED_TICKETS_FILE);
						
				
				if(fileInputStream.available() > 0){//---there some avilable tickets
					System.out.println("There is tickets (saving them...)");
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					List<Ticket> tickets = (List<Ticket>) objectInputStream.readObject();
					objectInputStream.close();
					fileInputStream.close();
					
					//--file content
					PrintWriter writer = new PrintWriter(AppConst.NOT_SAVED_TICKETS_FILE);
					writer.print("");
					writer.close();
					
					//---save tickets
					TicketRepository repository = new TicketRepository();
					for (Ticket ticket : tickets) {
						repository.create(ticket);
					}
					
				}	
				
				return null;
			}
			
		};
		saveWorker.execute();
	}

	/**
	 * 
	 * @param size
	 */
	public void setNumbeOfWaitingTickets(int nbTickets) {
		btnWaitingTickets.setText("En attente: "+nbTickets);
	}

	public void setUserName(String name) {
		lblUserName.setText(name);
		// TODO Auto-generated method stub
		
	}
}

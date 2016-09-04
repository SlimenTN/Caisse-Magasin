package smt.cm.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import smt.cm.comps.NumpadButton;
import smt.cm.entities.Ticket;
import smt.renderer.MoneyRenderer;
import smt.services.TabModel;

public class WaitingTicketDialog extends JDialog {
	private static final long serialVersionUID = -3627368103842484467L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private final String[] header = new String[]{"Numéro", "Date", "Total ticket"};
	private TabModel model;
	private Ticket selectedTicket = null;

	/**
	 * Create the dialog.
	 */
	public WaitingTicketDialog(CaisseInterface caisseInterface) {
		super(caisseInterface);
		setTitle("Tickets en attente");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 713, 402);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				
				model = new TabModel(header);
				table.setModel(model);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new NumpadButton("OK");
				okButton.addActionListener(e -> sendSelectedTicket());
				okButton.setText("Valider");
				okButton.setPreferredSize(new Dimension(120, 70));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new NumpadButton("Cancel");
				cancelButton.addActionListener(e -> setVisible(false));
				cancelButton.setText("Annuler");
				cancelButton.setPreferredSize(new Dimension(120, 70));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		initTableRenderer();
	}
	
	/**
	 * 
	 */
	private void sendSelectedTicket() {
		int sr = table.getSelectedRow();
		selectedTicket = (Ticket) model.getValueAt(sr, 0);
		setVisible(false);
	}

	private void initTableRenderer() {
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		MoneyRenderer mr = new MoneyRenderer();
		table.getColumnModel().getColumn(2).setCellRenderer(mr);
	}

	public void setListAndShow(List<Ticket> list){
		selectedTicket = null;
		List<Object> rows = new ArrayList<Object>();
		for (Ticket ticket : list) {
			List<Object> row = new ArrayList<Object>();
			row.add(ticket);
			row.add(ticket.getDateTicket());
			row.add(ticket.getTotTicket());
			
			rows.add(row);
		}
		model.reBuildTabModel(rows);
		table.getSelectionModel().setSelectionInterval(0, 0);
		setVisible(true);
	}

	public Ticket getSelectedTicket() {
		return selectedTicket;
	}
	
	

}

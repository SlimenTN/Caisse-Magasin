package smt.cm.comps;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;

import smt.cm.util.AppConst;
import smt.services.Services;

public class TicketRendererView extends JComponent{
	private static final long serialVersionUID = -524598628503798028L;
	
	private JLabel lblPrdDesignation;
	private JLabel lblUnitPrice;
	private JLabel lblQuantity;
	
	private Color selectionColor = Color.WHITE;
	
	private TicketRendererObject ticketRendererObject;
	private JLabel lblRemise;
	
	public TicketRendererView() {
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		
		lblPrdDesignation = new JLabel("Pain");
		lblPrdDesignation.setBounds(0, 0, 218, 19);
		lblPrdDesignation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblPrdDesignation);
		
		lblUnitPrice = new JLabel("Prix unitaire: 0,230");
		lblUnitPrice.setBounds(0, 19, 214, 19);
		lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblUnitPrice);
		
		lblQuantity = new JLabel("Quantit\u00E9: 4");
		lblQuantity.setBounds(0, 38, 100, 19);
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblQuantity);
		
		lblRemise = new JLabel("(Remise 10%)");
		lblRemise.setForeground(Color.GRAY);
		lblRemise.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblRemise.setBounds(101, 40, 99, 14);
		lblRemise.setVisible(false);
		add(lblRemise);
	}

	public TicketRendererObject getTicketRendererObject() {
		return ticketRendererObject;
	}

	public void setTicketRendererObject(TicketRendererObject ticketRendererObject) {
		this.ticketRendererObject = ticketRendererObject;
		fillComponents();
	}

	private void fillComponents() {
		lblPrdDesignation.setText(ticketRendererObject.getProduct().getDesignation());
		lblUnitPrice.setText("Prix unitaire: "+AppConst.numberFormat.format(ticketRendererObject.getProduct().getSalePrice()));
		Services.logConsole("is int: "+Services.isInteger(ticketRendererObject.getQuantity())+", inValue: "+ticketRendererObject.getQuantity().intValue(), AppConst.DEBUG_ON);
		if(Services.isInteger(ticketRendererObject.getQuantity()))
			lblQuantity.setText("Quantit\u00E9: "+ticketRendererObject.getQuantity().intValue());
		else
			lblQuantity.setText("Quantit\u00E9: "+ticketRendererObject.getQuantity());
		if(ticketRendererObject.getRemise() != null && ticketRendererObject.getRemise() != 0){
			lblRemise.setText("(Remise "+ticketRendererObject.getRemise()+"%)");
			lblRemise.setVisible(true);
		}else{
			lblRemise.setVisible(false);
		}
	}

	/**
	 * Switch the background color depending on the selection of ticket table
	 * @param isSelected
	 */
	public void switchSelection(boolean isSelected) {
		if(isSelected){
			selectionColor = AppConst.jtableSelectionColor;
			switchLabelsColor(Color.WHITE);
			lblRemise.setForeground(Color.WHITE);
		}else{
			selectionColor = Color.WHITE;
			switchLabelsColor(Color.BLACK);
			lblRemise.setForeground(Color.GRAY);
		}
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * 
	 * @param color
	 */
	private void switchLabelsColor(Color color) {
		lblPrdDesignation.setForeground(color);
		lblQuantity.setForeground(color);
		lblUnitPrice.setForeground(color);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.setColor(selectionColor);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
}

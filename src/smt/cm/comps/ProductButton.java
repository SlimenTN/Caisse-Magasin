package smt.cm.comps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import smt.cm.entities.Product;
import smt.cm.util.AppConst;
import smt.services.PanelImage;

public class ProductButton extends JPanel implements MouseListener{
	private static final long serialVersionUID = 3084112221884943650L;
	private final String IMAGES_PATH = "prd images/";
	private PanelImage prdImg;
	private JLabel prdPrice;
	private JLabel prdName;
	private Product product;
	
	public ProductButton(Product product) {
		this.product = product;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(90, 90));
		
		prdPrice = new JLabel();
		prdPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(prdPrice, BorderLayout.SOUTH);
		
		prdImg = new PanelImage();
		add(prdImg, BorderLayout.CENTER);
		prdName = new JLabel("Oeuf");
		prdImg.add(prdName);
		prdName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		addMouseListener(this);
		fillComponents();
	}
	
	private void fillComponents() {
		prdPrice.setText(""+AppConst.numberFormat.format(product.getSalePrice()));
		prdName.setText(product.getDesignation());
		String imgPath = (product.getImage() == null) ? IMAGES_PATH+"no_image.png" : IMAGES_PATH+product.getImage();
		try {
			prdImg.setImage(imgPath);
		} catch (IOException e) {}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		whenClickedDo();
	}

	/**
	 * Fire this function when mouse clicked
	 * override this methode for custom actions
	 */
	protected void whenClickedDo() {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}

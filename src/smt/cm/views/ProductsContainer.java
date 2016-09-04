package smt.cm.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import smt.cm.comps.CategoryButton;
import smt.cm.comps.ProductButton;
import smt.cm.comps.SToggleButton;
import smt.cm.entities.CategoryProduct;
import smt.cm.entities.Product;
import smt.cm.util.AppConst;
import smt.services.Services;
import smt.services.WrapLayout;

public class ProductsContainer extends JPanel{

	private static final long serialVersionUID = -7838014239041690718L;
	private JPanel codeBarrePanel;
	private JTextField barcodeField;
	private JToggleButton togglePrd;
	private JToggleButton toggleTicket;
	private JPanel prdCategoriesPanel;
	private JPanel listPrdPanel;
	private ButtonGroup codeBarreGroup = new ButtonGroup();
	private ButtonGroup categoriesGroup = new ButtonGroup();
	
	private CategoryButton ctgrbtnTous;
	private CaisseInterface caisseInterface;
	
	private List<Product> products = null;
	private List<CategoryProduct> categories = null;

	public ProductsContainer(CaisseInterface container) {
		this.caisseInterface = container;
		setLayout(new BorderLayout(0, 0));
		
		codeBarrePanel = new JPanel();
		codeBarrePanel.setBackground(Color.WHITE);
		codeBarrePanel.setPreferredSize(new Dimension(10, 50));
		add(codeBarrePanel, BorderLayout.NORTH);
		codeBarrePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		barcodeField = new JTextField();
		barcodeField.setHorizontalAlignment(SwingConstants.RIGHT);
		barcodeField.setFont(new Font("Tahoma", Font.BOLD, 20));
		barcodeField.setPreferredSize(new Dimension(300, 40));
		barcodeField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String barCode = barcodeField.getText();
				findProductByBarCode(barCode);
			}
		});
		barcodeField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				caisseInterface.setLastFocusedComponent(barcodeField);
			}
		});
		codeBarrePanel.add(barcodeField);
		
		togglePrd = new SToggleButton("P");
		togglePrd.setFont(new Font("Tahoma", Font.BOLD, 20));
		togglePrd.setSelected(true);
		codeBarreGroup.add(togglePrd);
		togglePrd.setMaximumSize(new Dimension(50, 50));
		togglePrd.setPreferredSize(new Dimension(50, 40));
		codeBarrePanel.add(togglePrd);
		
		toggleTicket = new SToggleButton("T");
		toggleTicket.setFont(new Font("Tahoma", Font.BOLD, 20));
		codeBarreGroup.add(toggleTicket);
		toggleTicket.setMaximumSize(new Dimension(50, 50));
		toggleTicket.setPreferredSize(new Dimension(50, 40));
		codeBarrePanel.add(toggleTicket);
		
		prdCategoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prdCategoriesPanel.setBackground(Color.WHITE);
		JScrollPane catPrdPanel = new JScrollPane(prdCategoriesPanel);
		
		CategoryProduct all = new CategoryProduct();
		all.setDesignation("TOUS");
		all.setBackground(null);
		ctgrbtnTous = new CategoryButton(all);
		ctgrbtnTous.setBackground(new Color(34, 139, 34));		
		ctgrbtnTous.setSelected(true);
		ctgrbtnTous.addActionListener(e -> displayeRelatedProducts(ctgrbtnTous));
		categoriesGroup.add(ctgrbtnTous);
		prdCategoriesPanel.add(ctgrbtnTous);
		
		catPrdPanel.setBorder(null);
		catPrdPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		add(catPrdPanel, BorderLayout.SOUTH);
		
		
		listPrdPanel = new JPanel(new WrapLayout(FlowLayout.LEFT));
		listPrdPanel.setBackground(Color.WHITE);
		JScrollPane listPrdScroll = new JScrollPane(listPrdPanel);
		listPrdScroll.setBorder(null);
		
		
		
		add(listPrdScroll, BorderLayout.CENTER);
	}

	/**
	 * Get product by barcode
	 * @param barCode
	 */
	protected void findProductByBarCode(String barCode) {
		for (Product product : products) {
			Services.logConsole("sent barcode: "+barCode+", produt barcode: "+product.getBarreCode(), AppConst.DEBUG_ON);
			if(product.getBarreCode() != null && product.getBarreCode().equals(barCode)){
				caisseInterface.sendProductToTicket(product);
				return;
			}
		}
		JOptionPane.showMessageDialog(caisseInterface, "Produit inconnue!", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Set needed data 
	 * @param products
	 */
	public void initListProductsAndCategories(List<Product> products) {
		this.products = products;
		initProducts();
		initCategories();
	}

	/**
	 * Init list products
	 */
	private void initProducts() {
		for (Product product : products) {
			if(product.getBarreCode() == null){
				@SuppressWarnings("serial")
				ProductButton prdButton = new ProductButton(product){
					@Override
					protected void whenClickedDo() {
						caisseInterface.sendProductToTicket(product);
					}
				};
				listPrdPanel.add(prdButton);
			}
		}
		listPrdPanel.revalidate();
	}

	/**
	 * init list categories
	 */
	private void initCategories() {
		categories = new ArrayList<CategoryProduct>();
		for (Product product : products) {
			CategoryProduct category = product.getCategoryProduct();
			if(!categoryExist(category)){
				categories.add(category);
				CategoryButton categoryButton = new CategoryButton(category);
				categoryButton.addActionListener(e -> displayeRelatedProducts(categoryButton));
				categoriesGroup.add(categoryButton);
				prdCategoriesPanel.add(categoryButton);
			}
		}
		prdCategoriesPanel.revalidate();
	}

	/**
	 * Displaye products by selected category
	 * @param categoryButton
	 */
	private void displayeRelatedProducts(CategoryButton categoryButton) {
		listPrdPanel.removeAll();
		if(categoryButton.getCategoryProduct().getDesignation().equals("TOUS")){
			initProducts();
		}else{
			for (Product product : products) {
				Services.logConsole("product: "+product+", prd cat: "+product.getCategoryProduct()+", cat button: "+categoryButton.getCategoryProduct()
						, AppConst.DEBUG_ON);
				if(product.getBarreCode() == null && product.getCategoryProduct().equals(categoryButton.getCategoryProduct())){
					@SuppressWarnings("serial")
					ProductButton prdButton = new ProductButton(product){
						@Override
						protected void whenClickedDo() {
							caisseInterface.sendProductToTicket(product);
						}
					};
					listPrdPanel.add(prdButton);
				}
			}
			listPrdPanel.revalidate();
		}
		listPrdPanel.repaint();
	}

	/**
	 * Check if category exist already
	 * @param category
	 * @return boolean
	 */
	private boolean categoryExist(CategoryProduct category) {
		for (CategoryProduct categoryProduct : categories) {
			if(categoryProduct.equals(category)) return true;
		}
		return false;
	}

	public void initPanel() {
		barcodeField.setText("");
		barcodeField.requestFocusInWindow();
	}

	public void focusBarcodeField() {
		barcodeField.requestFocusInWindow();
	}

}

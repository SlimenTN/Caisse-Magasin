package smt.cm.comps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import smt.buttons.ButtonStyleUI;
import smt.cm.entities.CategoryProduct;

public class CategoryButton extends JToggleButton{
	private static final long serialVersionUID = 5581580692244363696L;
	private final Color selectColor = Color.WHITE;
	private CategoryProduct categoryProduct;
	private Color categoryColor;

	public CategoryButton(CategoryProduct categoryProduct) {
		this.categoryProduct = categoryProduct;
		initBgColor();
		initUI();
	}

	private void initBgColor() {
		if(categoryProduct.getBackground() != null){
			String[] rgb = new String[3];
			rgb = categoryProduct.getBackground().split(",");
			categoryColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		}else{
			categoryColor = Color.DARK_GRAY;
		}
	}

	private void initUI() {
		setUI(new ButtonStyleUI());
		setText(categoryProduct.getDesignation());
		setPreferredSize(new Dimension(90, 90));
		setBackground(categoryColor);
		setForeground(Color.WHITE);
		setBorder(new LineBorder(categoryColor));
		
		addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					setBackground(selectColor);
					setForeground(categoryColor);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					setBackground(categoryColor);
					setForeground(Color.WHITE);
				}
			}
		});
		
	}

	public CategoryProduct getCategoryProduct() {
		return categoryProduct;
	}

	public void setCategoryProduct(CategoryProduct categoryProduct) {
		this.categoryProduct = categoryProduct;
	}
	
	
	
}

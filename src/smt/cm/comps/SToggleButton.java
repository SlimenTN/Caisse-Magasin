package smt.cm.comps;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import smt.buttons.ButtonStyleUI;

public class SToggleButton extends JToggleButton {
	private static final long serialVersionUID = 8353672854183061116L;
	private Color basicColor = Color.WHITE;
	private Color unselectedTxtColor = Color.RED;
	private Color selectedColor = Color.RED;
	private final LineBorder borderColor = new LineBorder(Color.RED, 1);

	public SToggleButton() {
		initUI();
	}

	public SToggleButton(String text) {
		setText(text);
		initUI();
	}

	private void initUI() {
		setUI(new ButtonStyleUI());
		setBackground(basicColor);
		setForeground(unselectedTxtColor);
		setBorder(borderColor);
		
		addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					setBackground(selectedColor);
					setForeground(Color.WHITE);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					setBackground(basicColor);
					setForeground(unselectedTxtColor);
				}
			}
		});
	}

}

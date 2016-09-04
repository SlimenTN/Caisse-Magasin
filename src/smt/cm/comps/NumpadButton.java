package smt.cm.comps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import smt.buttons.ButtonStyleUI;

public class NumpadButton extends JButton{
	private static final long serialVersionUID = -6066656831492653128L;
	private Color basicColor = new Color(221, 221, 221);
	private Color pressColor = new Color(64, 64, 64);
	
	public NumpadButton(String text) {
		setText(text);
		initUI();
	}
	
	public NumpadButton() {
		initUI();
	}

	private void initUI() {
		setUI(new ButtonStyleUI());
		
		setBackground(basicColor);
		setFont(new Font("Century gothic", Font.PLAIN, 20));
		setPreferredSize(new Dimension(70, 70));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(pressColor);
				setForeground(Color.WHITE);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(basicColor);
				setForeground(Color.BLACK);
			}
		});
	}
}

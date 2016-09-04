package smt.cm.comps;

import java.awt.Dimension;
import smt.cm.entities.ModeRegulation;

public class ModeRegulationButton extends NumpadButton{
	private static final long serialVersionUID = -2665240480953954588L;
	private ModeRegulation modeRegulation;
	
	public ModeRegulationButton(ModeRegulation modeRegulation) {
		this.modeRegulation = modeRegulation;
		initButton();
	}
	private void initButton() {
		setPreferredSize(new Dimension(150, 90));
		setText(modeRegulation.getDesignation());
	}
	public ModeRegulation getModeRegulation() {
		return modeRegulation;
	}
	public void setModeRegulation(ModeRegulation modeRegulation) {
		this.modeRegulation = modeRegulation;
	}
	
	

}

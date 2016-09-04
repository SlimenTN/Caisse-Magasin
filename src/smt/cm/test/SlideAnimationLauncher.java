package smt.cm.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import smt.cm.views.AuthentificationPanel;
import smt.cm.views.PayTicketPanel;
import smt.cm.views.StandByPanel;
import smt.cm.views.SuccessAuthentificationPanel;

public class SlideAnimationLauncher {

	static public void main(final String[] args) throws Exception {
	    SwingUtilities.invokeAndWait(new Runnable() {
	        @Override
	        public void run() {
	            final JFrame jFrame = new JFrame() {
					private static final long serialVersionUID = -817372815343073952L;

					{
	                    final PanelSlider42<JFrame> slider = new PanelSlider42<JFrame>(this);
	                    final JPanel jPanel = slider.getBasePanel();

	                    slider.addComponent(new AuthentificationPanel(null));
	                    slider.addComponent(new SuccessAuthentificationPanel(null));
	                    slider.addComponent(new StandByPanel(null));
	                    slider.addComponent(new PayTicketPanel(null));

	                    getContentPane().add(jPanel);
	                    setSize(300, 300);
	                    setLocationRelativeTo(null);
	                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                }
	            };
	            jFrame.setVisible(true);
	        }
	    });
	}
}

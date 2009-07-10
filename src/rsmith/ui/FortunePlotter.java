package rsmith.ui;

import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;

public class FortunePlotter extends Thread {
	
	private JPanel panel = null;
	private FortuneAlgorithm fortune = null;
	
	public void FortunePlotter() {}
	
	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}
	/**
	 * @param panel the panel to set
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public void setFortune(FortuneAlgorithm fortune) {
		this.fortune = fortune;
	}
	public FortuneAlgorithm getFortune() {
		return fortune;
	}
}

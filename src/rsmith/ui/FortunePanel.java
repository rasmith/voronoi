package rsmith.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class FortunePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1664625747676781533L;
	private FortunePlotter plotter = null;

	public FortunePanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		plotter = new FortunePlotter(this);
		plotter.start();
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public void repaint() {
		super.repaint();
		System.out.println("repaint");
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("paintComponent");
	}
}
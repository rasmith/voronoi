package rsmith.ui;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;
import rsmith.util.PointUtils;

public class FortunePlotter extends Thread {

	private JPanel panel = null;
	private FortuneAlgorithm fortune = null;
	private Set<Point2D> points = null;
	
	public FortunePlotter(JPanel panel) {
		this.panel = panel;
		init();
	}

	private void init() {
		points = new HashSet<Point2D>();
		for (int i = 0; i < 10; i++) {
			points.add(PointUtils.randomPoint(200, 500));
		}
		fortune = new FortuneAlgorithm(points);	
	}
	
	public void draw() {
	}

	public void run() {
		try {
			while (!fortune.isFinished()) {
				draw();
				fortune.step();
				sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel
	 *            the panel to set
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

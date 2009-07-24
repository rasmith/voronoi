package rsmith.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;
import rsmith.fortune.VoronoiNode;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.fortune.point.VoronoiPoint;
import rsmith.geom.Line;
import rsmith.geom.Quadratic;
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
		/**Point2D.Double [] testPoints  = {
				new Point2D.Double(214.33016065965256,267.26155674871404),
				new Point2D.Double(185.91982703582931,173.9413503911116),
				new Point2D.Double(249.17348213997917,176.29054413956277),
				new Point2D.Double(146.16226904173257,200.41007959663432),
				new Point2D.Double(171.2625894302044,167.25732810645806),
				new Point2D.Double(189.694992382948,276.5030262983955),
				new Point2D.Double(278.42154200153936,219.55123585241193),
				new Point2D.Double(280.1723056750386,125.31368823793328),
				new Point2D.Double(141.66704014556166,130.00460088413675),
				new Point2D.Double(160.20875408755992,201.71569526342256)};**/
		Point2D.Double [] testPoints = {
				new Point2D.Double(237.36119290954156,255.6685266151402),
				new Point2D.Double(179.7875967515497,148.47826578522196),
				new Point2D.Double(167.48144769934447,268.18940438068785),
				new Point2D.Double(116.27080806031805,230.90621514713007),
				new Point2D.Double(151.74754191370482,294.95082985362603),
				new Point2D.Double(221.42612211139917,166.77211211609512),
				new Point2D.Double(128.94029585646277,281.5510405891649),
				new Point2D.Double(128.31076682011366,116.85176995793893),
				new Point2D.Double(178.5950932404774,168.49640907388522),
				new Point2D.Double(118.54249622085746,183.45825202723847)
		};
		points = new HashSet<Point2D>();
		for (int i = 0; i < 10; i++) {
			Point2D p = testPoints[i];
			//Point2D p = PointUtils.randomPoint(100,300);
			//System.out.print("new Point2D.Double("+p.getX()+","+p.getY()+"),");
			points.add(p);
		}
		fortune = new FortuneAlgorithm(points);
		fortune.init();
	}

	public void drawLine(Line l) {
		Graphics g = panel.getGraphics();
		Line below = new Line(0, 0);
		Line above = new Line(0, panel.getHeight());
		Point2D pFrom = l.intersect(above);
		Point2D pTo = l.intersect(below);
		g.drawLine((int) pFrom.getX(), (int) pFrom.getY(), (int) pTo.getX(),
				(int) pTo.getY());
	}

	public void drawPoint2D(Point2D p) {
		Graphics g = panel.getGraphics();
		Color c = new Color(0xFF0000);
		g.setColor(c);
		g.fillOval((int) p.getX()-2, (int) p.getY()-2, 5, 5);
	}

	public void drawHorizontalLine(double value) {
		Graphics g = panel.getGraphics();
		Color c = new Color(0x0000FF);
		g.setColor(c);
		g.drawLine(0, (int) value, (int) panel.getWidth(), (int) value);
	}

	public void drawVerticalLine(double xval, double yfrom, double yto) {
		Graphics g = panel.getGraphics();
		g.drawLine((int) xval, (int) yfrom, (int) xval, (int) yto);
	}

	public void drawQuadratic(Quadratic q, double fromX, double toX) {
		Graphics g = panel.getGraphics();
		double delta = 0.05;
		for (double xval = fromX; xval <= toX - delta; xval = Math.min(xval
				+ delta, toX)) {
			if (xval < toX) {
				double xBegin = xval;
				double xEnd = Math.min(xval + delta, toX);
				double yBegin = q.eval(xBegin);
				double yEnd = q.eval(xEnd);
				g.drawLine((int) xBegin, (int) yBegin, (int) xEnd, (int) yEnd);
			}
		}
	}

	public void clearGraphics() {
		Graphics g = panel.getGraphics();
		g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
	}
	
	public void draw() {
		Iterator<VoronoiNode> iter = fortune.getFortuneData().getBeachline()
				.iterator();
		panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
		double sweep = getFortune().getFortuneData().getSweepY();
		drawHorizontalLine(sweep);
		while (iter.hasNext()) {
			VoronoiNode node = iter.next();
			VoronoiPoint vp = node.getPoint();
			if (vp instanceof BreakPoint) {
				drawBreakPoint((BreakPoint)vp);
			} else {
				drawSitePoint((SitePoint)vp);
			}
		}
	}
	
	
	public void drawBreakPoint(BreakPoint b) {
		drawPoint2D(b.getPosition());
		if(b.hasSiteAtSweep() && b.getNext() != null && b.getNext().hasSiteAtSweep()) {
			drawVerticalLine(b.getPosition().getX(),this.getFortune().getFortuneData().getSweepY(), b.getPosition().getY());
		} 
		
		double sweep = getFortune().getFortuneData().getSweepY();
		Quadratic q = b.getRight().createQuadratic(sweep);
		
		if(b.getPrevious() == null) {
			Quadratic r = b.getLeft().createQuadratic(sweep);
			this.drawQuadratic(r, b.intersectLeft(panel.getHeight())[0], b.getPosition().getX());
		}
		
		if(!(b.hasSiteAtSweep() && b.getNext() != null && b.getNext().hasSiteAtSweep())) {
			double toX = (b.getNext() != null ? b.getNext().getPosition().getX() : b.intersectRight(panel.getHeight())[1]);
			this.drawQuadratic(q, b.getPosition().getX(), toX);
		}
	}

	public void drawSitePoint(SitePoint s) {
	}
	
	public void run() {
		try {
			while (!fortune.isFinished()) {
				if (panel.getGraphics() != null) {
					fortune.step();
					draw();
					
				}
				sleep(500);
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

package rsmith.ui;

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


public class FortunePlotter extends Thread {

	private JPanel panel = null;
	private FortuneAlgorithm fortune = null;
	private Set<Point2D> points = null;

	public FortunePlotter(JPanel panel) {
		this.panel = panel;
		init();
	}

	private void init() {
		Point2D.Double [] testPoints  = {
				new Point2D.Double(214.33016065965256,267.26155674871404),
				new Point2D.Double(185.91982703582931,173.9413503911116),
				new Point2D.Double(249.17348213997917,176.29054413956277),
				new Point2D.Double(146.16226904173257,200.41007959663432),
				new Point2D.Double(171.2625894302044,167.25732810645806),
				new Point2D.Double(189.694992382948,276.5030262983955),
				new Point2D.Double(278.42154200153936,219.55123585241193),
				new Point2D.Double(280.1723056750386,125.31368823793328),
				new Point2D.Double(141.66704014556166,130.00460088413675),
				new Point2D.Double(160.20875408755992,201.71569526342256)};
		
		points = new HashSet<Point2D>();
		for (int i = 0; i < 10; i++) {
			Point2D p = testPoints[i];//PointUtils.randomPoint(100,300);
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
		g.drawOval((int) p.getX(), (int) p.getY(), 2, 2);
	}

	public void drawHorizontalLine(double value) {
		Graphics g = panel.getGraphics();
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

	public void _draw() {
		/**
		FortuneData data = fortune.getFortuneData();
		Iterator<VoronoiNode> iter =data.getBeachline().iterator();
		double sweepY = data.getSweepY();
		VoronoiNode last = null;
		VoronoiNode current = null;
		System.out.print("[");
		while(iter.hasNext()) {
			current = iter.next();
			if(current.getPoint() instanceof BreakPoint) {
				BreakPoint bp = (BreakPoint)current.getPoint();
				BreakPoint bpn = bp.getNext();
				if(bpn != null && bpn.getPosition().getX() < bp.getPosition().getX()) {
					System.out.println("Found breakpoints out of order (bp,bpn):"+bp.getPosition()+","+bpn.getPosition());
				}
				BreakPoint bpp = bp.getPrevious();
				if(bpp != null && bpp.getPosition().getX() > bp.getPosition().getX()) {
					System.out.println("Found breakpoints out of order (bp,bpp)"+ bp.getPosition() + "," + bpp.getPosition());
				}
			} else {
				if(data.getBeachline().size() > 1) {
					System.out.println("Found a site point when should not have.");
				}
			}
		}
		System.out.println("]");**/
	}
	
	public void draw() {
		Iterator<VoronoiNode> iter = fortune.getFortuneData().getBeachline()
				.iterator();
		panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
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
		if(b.hasSiteAtSweep()) {
		} else {
			if(b.getPrevious() == null) {;
			} else if(b.getNext() == null) {
			} else {
				drawPoint2D(b.getPosition());
				drawPoint2D(b.getNext().getPosition());
				drawQuadratic(	b.getRight().createQuadratic(this.getFortune().getFortuneData().getSweepY()),
								b.getPosition().getX(),
								b.getNext().getPosition().getX());
			}
		}
	}

	public void drawSitePoint(SitePoint s) {
	}
	
	public void run() {
		try {
			while (!fortune.isFinished()) {
				if (panel.getGraphics() != null) {
					draw();
					// System.out.println("");
					fortune.step();
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

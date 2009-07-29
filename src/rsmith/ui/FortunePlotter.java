package rsmith.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;
import rsmith.fortune.VoronoiNode;
import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.event.SiteEvent;
import rsmith.fortune.event.SweepEvent;
import rsmith.fortune.point.AbstractPoint;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.fortune.point.VoronoiPoint;
import rsmith.geom.CoordinateMap;
import rsmith.geom.Line;
import rsmith.geom.Quadratic;
import rsmith.util.NumberUtils;
import rsmith.util.PointUtils;

public class FortunePlotter extends Thread {

	private JPanel panel = null;
	private FortuneAlgorithm fortune = null;
	private Set<Point2D> points = null;
	private CoordinateMap cmap = null;
	private boolean initialized = false;

	public FortunePlotter(JPanel panel) {
		this.panel = panel;
	}

	private void init() {
		if (panel.getGraphics() == null) {
			return;
		}
		initialized = true;

		/**
		 * Point2D.Double [] testPoints = { new
		 * Point2D.Double(214.33016065965256,267.26155674871404), new
		 * Point2D.Double(185.91982703582931,173.9413503911116), new
		 * Point2D.Double(249.17348213997917,176.29054413956277), new
		 * Point2D.Double(146.16226904173257,200.41007959663432), new
		 * Point2D.Double(171.2625894302044,167.25732810645806), new
		 * Point2D.Double(189.694992382948,276.5030262983955), new
		 * Point2D.Double(278.42154200153936,219.55123585241193), new
		 * Point2D.Double(280.1723056750386,125.31368823793328), new
		 * Point2D.Double(141.66704014556166,130.00460088413675), new
		 * Point2D.Double(160.20875408755992,201.71569526342256)};
		 */

		/**
		 * Point2D.Double [] testPoints = { new
		 * Point2D.Double(237.36119290954156,255.6685266151402), new
		 * Point2D.Double(179.7875967515497,148.47826578522196), new
		 * Point2D.Double(167.48144769934447,268.18940438068785), new
		 * Point2D.Double(116.27080806031805,230.90621514713007), new
		 * Point2D.Double(151.74754191370482,294.95082985362603), new
		 * Point2D.Double(221.42612211139917,166.77211211609512), new
		 * Point2D.Double(128.94029585646277,281.5510405891649), new
		 * Point2D.Double(128.31076682011366,116.85176995793893), new
		 * Point2D.Double(178.5950932404774,168.49640907388522), new
		 * Point2D.Double(118.54249622085746,183.45825202723847) };
		 */

		/***********************************************************************
		 * Point2D.Double[] testPoints = {
		 * 
		 * new Point2D.Double(147.0732584314681, 173.80750919307016), new
		 * Point2D.Double(263.31902555019417, 110.70763416050802), new
		 * Point2D.Double(200.60694344015295, 135.30725512849017), new
		 * Point2D.Double(106.74378961632263, 166.5574141904719), new
		 * Point2D.Double(170.78343469042176, 178.70935879334104), new
		 * Point2D.Double(234.51289732321888, 176.90372302999197), new
		 * Point2D.Double(287.2844812704159, 233.79119934395928), new
		 * Point2D.Double(103.41700707279446, 116.56491337735835), new
		 * Point2D.Double(120.59588768125184, 119.62844811685294), new
		 * Point2D.Double(231.05151947085594, 295.2391255003456) };
		 **********************************************************************/

		/**
		 * Point2D.Double[] testPoints = { new
		 * Point2D.Double(225.07572734218084, 373.3250018589953), new
		 * Point2D.Double(453.6545808366297, 354.2050780216746), new
		 * Point2D.Double(261.260290385394, 343.2367065919145), new
		 * Point2D.Double(348.1461860932294, 231.67015557305558), new
		 * Point2D.Double(434.84505860429806, 205.36575235215395) };
		 */
		int w = panel.getWidth();
		int h = panel.getHeight();

		cmap = new CoordinateMap(new Rectangle2D.Double(0, 800, 1000, 1000),
				new Rectangle2D.Double(0, 0, w, h));

		points = new HashSet<Point2D>();

		for (int i = 0; i < 100; i++) {
			// Point2D p = testPoints[i];
			Point2D p = new Point2D.Double(NumberUtils.randomNumber(w / 4,
					3 * w / 4), NumberUtils.randomNumber(h / 4, 3 * h / 4));
			System.out.print("new Point2D.Double(" + p.getX() + "," + p.getY()
					+ "),");
			points.add(p);
		}
		fortune = new FortuneAlgorithm(points);
		fortune.init();
	}

	public void clearGraphics() {
		Graphics g = panel.getGraphics();
		g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
	}

	public void drawPoint2D(Point2D p) {
		drawPoint2D(p, Color.BLACK);
	}

	public void drawPoint2D(Point2D p, Color c) {
		Graphics g = panel.getGraphics();
		Point2D q = cmap.map(p);
		g.setColor(c);
		g.fillOval((int) q.getX() - 3, (int) q.getY() - 3, 6, 6);
	}

	public void drawLine(Point2D p1, Point2D p2) {
		Graphics g = panel.getGraphics();
		Point2D q1 = cmap.map(p1);
		Point2D q2 = cmap.map(p2);
		g.drawLine((int) q1.getX(), (int) q1.getY(), (int) q2.getX(), (int) q2
				.getY());
	}

	public void drawLine(Line l) {
		Line below = new Line(0, cmap.getSourceRect().getY());
		Line above = new Line(0, cmap.getSourceRect().getY()
				+ cmap.getSourceRect().getWidth());
		drawLine(l.intersect(above), l.intersect(below));
	}

	public void drawLine(double x1, double y1, double x2, double y2) {
		drawLine(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
	}

	public void drawHorizontalLine(double value) {
		drawLine(cmap.getSourceRect().getX(), value, cmap.getSourceRect()
				.getX()
				+ cmap.getSourceRect().getWidth(), value);
	}

	public void drawVerticalLine(double xval, double yfrom, double yto) {
		drawLine(xval, yfrom, xval, yto);
	}

	public void drawCircleAtCenter(Point2D p, double r, Color c) {
		Point2D q = cmap.map(p);
		double s = cmap.maplength(r);
		int w = (int) (2 * s);
		int h = (int) (2 * s);
		int x = (int) q.getX() - w / 2;
		int y = (int) q.getY() - w / 2;
		Graphics g = panel.getGraphics();
		g.setColor(c);
		g.drawOval(x, y, w, h);
	}

	public void drawQuadratic(Quadratic q, double fromX, double toX) {
		double delta = 0.5;
		for (double xval = fromX; xval <= toX - delta; xval = Math.min(xval
				+ delta, toX)) {
			if (xval < toX) {
				double xBegin = xval;
				double xEnd = Math.min(xval + delta, toX);
				double yBegin = q.eval(xBegin);
				double yEnd = q.eval(xEnd);
				drawLine(xBegin, yBegin, xEnd, yEnd);
			}
		}
	}

	public void drawBreakPoint(BreakPoint b) {
		Color bc = (b.getNode().getCircleEvent() == null ? Color.RED
				: Color.GREEN);
		drawPoint2D(b.getPosition(), bc);
		if (b.hasSiteAtSweep() && b.getNext() != null
				&& b.getNext().hasSiteAtSweep()) {
			drawVerticalLine(b.getPosition().getX(), this.getFortune()
					.getFortuneData().getSweepY(), b.getPosition().getY());
		}

		double sweep = getFortune().getFortuneData().getSweepY();
		Quadratic q = b.getRight().createQuadratic(sweep);

		if (b.getPrevious() == null) {
			Quadratic r = b.getLeft().createQuadratic(sweep);
			this.drawQuadratic(r, b.intersectLeft(cmap.getSourceRect()
					.getHeight())[0], b.getPosition().getX());
		}

		if (!(b.hasSiteAtSweep() && b.getNext() != null && b.getNext()
				.hasSiteAtSweep())) {
			double toX = (b.getNext() != null ? b.getNext().getPosition()
					.getX() : b
					.intersectRight(cmap.getSourceRect().getHeight())[1]);
			this.drawQuadratic(q, b.getPosition().getX(), toX);
		}
	}

	public void drawSitePoint(SitePoint s) {
		drawPoint2D(s.getPosition(), (s.isProcessed() ? Color.MAGENTA
				: Color.BLUE));
	}

	public void drawBeachline() {
		Iterator<VoronoiNode> iter = fortune.getFortuneData().getBeachline()
				.iterator();
		double sweep = getFortune().getFortuneData().getSweepY();
		drawHorizontalLine(sweep);
		while (iter.hasNext()) {
			VoronoiNode node = iter.next();
			VoronoiPoint vp = node.getPoint();
			if (vp instanceof BreakPoint) {
				drawBreakPoint((BreakPoint) vp);
			}
		}
	}

	public void drawSites() {
		Set<SitePoint> points = this.fortune.getFortuneData().getSites();
		Iterator<SitePoint> iter = points.iterator();
		while (iter.hasNext()) {
			drawSitePoint(iter.next());
		}
	}

	public void drawCurrentEvent() {
		SweepEvent e = fortune.getFortuneData().getEventQueue().peek();
		if (e != null) {
			if (e instanceof CircleEvent) {
				drawCircleEvent((CircleEvent) e);
			} else {
				drawSiteEvent((SiteEvent) e);
			}
		}
	}

	private void drawSiteEvent(SiteEvent e) {
		drawPoint2D(e.getSite().getPosition(), Color.YELLOW);
	}

	private void drawCircleEvent(CircleEvent e) {
		drawCircleAtCenter(e.getCenter(), e.getRadius(), Color.CYAN);
		drawPoint2D(e.getCenter(), Color.ORANGE);
		drawPoint2D(e.getPi().getPosition(), Color.WHITE);
		drawPoint2D(e.getPj().getPosition(), Color.WHITE);
		drawPoint2D(e.getPk().getPosition(), Color.WHITE);
	}

	public void draw() {
		fortune.step();
		panel.getGraphics()
				.clearRect(0, 0, panel.getWidth(), panel.getHeight());
		drawBeachline();
		drawSites();
		drawCurrentEvent();
	}

	public void run() {
		try {
			while (!initialized || !this.fortune.isFinished()) {
				if (!initialized) {
					init();
				} else {
					draw();
					sleep(50);
				}
			}
			Rectangle2D box = AbstractPoint.getBoundingBox();
			System.out.println("Bounding box: (x,y,w,h)=(" + box.getX() + ","
					+ box.getY() + "," + box.getWidth() + "," + box.getHeight()
					+ ")");
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

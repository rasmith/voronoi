package rsmith.ui;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;
import rsmith.fortune.FortuneData;
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
		points = new HashSet<Point2D>();
		for (int i = 0; i < 10; i++) {
			points.add(PointUtils.randomPoint(250, 300));
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

	public void draw() {
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
			}
		}
		System.out.println("]");
	}
	
	public void _draw() {
		Iterator<VoronoiNode> iter = fortune.getFortuneData().getBeachline()
				.iterator();
		double sweepY = fortune.getFortuneData().getSweepY();
		double fromX = 0;
		double toX = 0;
		Quadratic q = null;
		clearGraphics();
		drawHorizontalLine(sweepY);
		Line l = new Line(0, 800);
		double[] xvals = null;
		while (iter.hasNext()) {
			VoronoiNode node = iter.next();
			VoronoiPoint vp = node.getPoint();
			if (vp instanceof BreakPoint) {
				BreakPoint b = (BreakPoint) (vp);
				SitePoint left = b.getLeft();
				SitePoint right = b.getRight();
				BreakPoint next = b.getNext();
				BreakPoint previous = b.getPrevious();
				Point2D pos = b.getPosition();
				drawPoint2D(pos);
				if (left != null && right != null) {
					Line bis = Line.bisector(left.getPosition(), right
							.getPosition());
					drawLine(bis);
				}
				if (previous == null) {
					q = left.createQuadratic(sweepY);
					xvals = q.intersectLine(l);
					fromX = Math.max(0, xvals[0]);
					toX = pos.getX();
				} else {
					q = right.createQuadratic(sweepY);
					fromX = pos.getX();
					if (next == null) {
						xvals = q.intersectLine(l);
						toX= Math.min(panel.getWidth(), xvals[1]);
					} else {
						toX = next.getPosition().getX();
					}
				}
			} else {
				SitePoint s = (SitePoint) (vp);
				q = s.createQuadratic(sweepY);
				if(xvals != null) {
					xvals = q.intersectLine(l);
					fromX = Math.max(0, xvals[0]);
					toX = Math.min(panel.getWidth(), xvals[1]);
				}
			}
			if (q != null) {
				// drawQuadratic(q, 0 , 600);
				if (q.getA() == Double.POSITIVE_INFINITY
						|| q.getB() == Double.NEGATIVE_INFINITY) {
					Point2D pos = vp.getPosition();
					drawVerticalLine(pos.getX(), pos.getY(), sweepY);
				} else {
					xvals = q.intersectLine(l);
					drawQuadratic(q,fromX,toX);

					// if (xvals != null) {
					// fromX = Math.max(0, Math.min(xvals[0], xvals[1]));
					// toX = Math.min(panel.getWidth(), Math.max(xvals[0],
					// xvals[1]));

					// System.out.println("q=" + q + ",xvals.size="
					// + xvals.length + ",xvals[0]=" + xvals[0]
					// + ",xvals[1]=" + xvals[1]);

					// drawQuadratic(q, fromX, toX);
					// }
				}
			} else {
				// System.out.println("No site found, vp=" + vp);
			}
		}
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

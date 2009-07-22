package rsmith.fortune;

import java.awt.geom.Point2D;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.event.SiteEvent;
import rsmith.fortune.event.SweepEvent;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;

public class FortuneData {

	private PriorityQueue<SweepEvent> eventQueue;
	private NavigableSet<VoronoiNode> beachline;
	private NavigableSet<SitePoint> sites;
	private Set<Point2D> points;
	private double sweepY;

	/**
	 * @param p
	 */
	public FortuneData(Set<Point2D> p) {
		points = p;
	}

	public void init() {
		eventQueue = new PriorityQueue<SweepEvent>(new TreeSet<SweepEvent>());
		beachline = new TreeSet<VoronoiNode>();
		sites = new TreeSet<SitePoint>();
	}

	/**
	 * @return event queue
	 */
	public PriorityQueue<SweepEvent> getEventQueue() {
		return eventQueue;
	}

	/**
	 * @param q
	 */
	public void setEventQueue(PriorityQueue<SweepEvent> q) {
		eventQueue = q;
	}

	/**
	 * @return the beachline which is the status for this algorithm
	 */
	public NavigableSet<VoronoiNode> getBeachline() {
		return beachline;
	}

	/**
	 * @param beachline
	 *            the TreeSet representing the beachline
	 */
	public void setBeachline(NavigableSet<VoronoiNode> beachline) {
		this.beachline = beachline;
	}

	/**
	 * @return the sites
	 */
	public NavigableSet<SitePoint> getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	/**
	 * @param sites
	 */
	public void setSites(NavigableSet<SitePoint> sites) {
		this.sites = sites;
	}

	/**
	 * @return the points
	 */
	public Set<Point2D> getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	/**
	 * @param points
	 */
	public void setPoints(NavigableSet<Point2D> points) {
		this.points = points;
	}

	/**
	 * @return returns the current y-value of the sweep line
	 */
	public double getSweepY() {
		return sweepY;
	}

	/**
	 * @param sweepY
	 */
	public void setSweepY(double sweepY) {
		this.sweepY = sweepY;
	}

	/**
	 * @param p
	 */
	public void insertSitePoint(Point2D p) {
		SitePoint s = new SitePoint(p);
		sites.add(s);
		SiteEvent se = new SiteEvent(s);
		se.setFortuneData(this);
		eventQueue.add(se);
	}

	/**
	 * @param b
	 * @param previous
	 * @param next
	 * @param left
	 * @param right
	 * @return
	 */
	public VoronoiNode insertBreakPoint(BreakPoint b, BreakPoint previous,
			BreakPoint next, SitePoint left, SitePoint right) {
		b.setPrevious(previous);
		b.setNext(next);
		b.setLeft(left);
		b.setRight(right);
		VoronoiNode node = new VoronoiNode(b, null, this);
		b.setNode(node);
		beachline.add(node);
		return node;
	}

	/**
	 * @param b
	 */
	public void removeBreakPoint(BreakPoint b) {
		beachline.remove(b.getNode());
	}

	/**
	 * @param vb1
	 * @param vb2
	 */
	public void insertCircleEvents(VoronoiNode vb1, VoronoiNode vb2) {
		// see if there is an arc before the one in between b1 and b2
		BreakPoint b1 = (BreakPoint) vb1.getPoint();
		BreakPoint b2 = (BreakPoint) vb2.getPoint();
		BreakPoint bl = b1.getPrevious();
		BreakPoint br = b1.getNext();
		if (bl != null) {
			insertCircleEvent(bl, b1);
		}
		if (br != null) {
			insertCircleEvent(b2, br);
		}
	}

	/**
	 * @param left
	 * @param right
	 */
	public void insertCircleEvent(BreakPoint left, BreakPoint right) {
		if (left == null || right == null) {
			return;
		}

		CircleEvent c = CircleEvent.createCircleEvent(sweepY, left.getLeft(),
				left.getRight(), right.getRight());
		if (c != null) {
			c.setLeftBP(left);
			c.setRightBP(right);
			left.getNode().setCircleEvent(c);
			eventQueue.add(c);
		}
	}

	/**
	 * @param b
	 */
	public void clearCircleEvent(BreakPoint b) {
		if (b != null) {
			VoronoiNode node = b.getNode();
			CircleEvent event = node.getCircleEvent();
			if (event != null) {
				eventQueue.remove(event);
				node.setCircleEvent(null);
			}
		}
	}
}

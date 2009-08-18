package rsmith.fortune;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.TreeSet;

import rsmith.dcel.DCEL;
import rsmith.dcel.HalfEdge;
import rsmith.dcel.Vertex;
import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.event.SiteEvent;
import rsmith.fortune.event.SweepEvent;
import rsmith.fortune.point.Arc;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.geom.Line;

public class FortuneData {

	private PriorityQueue<SweepEvent> eventQueue;
	private NavigableSet<VoronoiNode> beachline;
	private NavigableSet<SitePoint> sites;
	private Set<Point2D> points;
	private DCEL edgeList;
	private Deque<BreakPoint> danglingEdges;
	private double sweepY;
	private SweepEvent currentEvent;
	private Rectangle2D boundingBox;

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
		edgeList = new DCEL();
		danglingEdges = new ArrayDeque<BreakPoint>();
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

	public Arc findArcAbove(SitePoint p) {
		Arc result = null;
		VoronoiNode vp = new VoronoiNode(p, null, this);
		VoronoiNode before = beachline.floor(vp);
		VoronoiNode after = beachline.ceiling(vp);
		BreakPoint left = (before != null ? (BreakPoint) before.getPoint()
				: null);
		BreakPoint right = (after != null ? (BreakPoint) after.getPoint()
				: null);
		result = new Arc(left, right);
		return result;
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
		if (previous != null) {
			previous.setNext(b);
		}
		if (next != null) {
			next.setPrevious(b);
		}
		beachline.add(node);
		return node;
	}

	/**
	 * @param b
	 */
	public void removeBreakPoint(BreakPoint b) {
		BreakPoint previous = b.getPrevious();
		BreakPoint next = b.getNext();
		if (previous != null) {
			previous.setNext(next);
		}
		if (next != null) {
			next.setPrevious(previous);
		}
		boolean result = beachline.remove(b.getNode());
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
		BreakPoint br = b2.getNext();
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

	/**
	 * @return
	 */
	public SweepEvent getCurrentEvent() {
		return currentEvent;
	}

	/**
	 * @param currentEvent
	 */
	public void setCurrentEvent(SweepEvent currentEvent) {
		this.currentEvent = currentEvent;
	}

	/**
	 * @return
	 */
	public DCEL getEdgeList() {
		return edgeList;
	}

	/**
	 * @param edgeList
	 */
	public void setEdgeList(DCEL edgeList) {
		this.edgeList = edgeList;
	}

	/**
	 * 
	 * @param b
	 */
	protected void insertHalfEdge(BreakPoint b) {
		HalfEdge edge = new HalfEdge();
		b.setEdge(edge);
		edgeList.getEdges().add(edge);
	}

	/**
	 * 
	 * @param b
	 * @param v
	 */
	protected void insertHalfEdge(BreakPoint b, Vertex v) {
		this.insertHalfEdge(b);
		b.getEdge().setOrigin(v);
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public void updateEdgeList(CircleEvent e, BreakPoint b) {

		BreakPoint left = e.getLeftBP();
		BreakPoint right = e.getRightBP();
		Vertex v = new Vertex();
		// updating the edgeList at a breakpoint
		Point2D pos = left.getPosition();
		v.setPosition(pos);

		updateEdge(left, v);
		updateEdge(right, v);
		insertHalfEdge(b, v);
	}

	protected void updateEdge(BreakPoint b, Vertex v) {
		HalfEdge edge = null;
		edge = b.getEdge();
		HalfEdge twin = new HalfEdge();
		twin.setOrigin(v);
		edge.setTwin(twin);
		twin.setTwin(edge);
		if (edge.getOrigin() == null) {
			danglingEdges.addLast(b);
		}
	}

	public Rectangle2D getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle2D boundingBox) {
		this.boundingBox = boundingBox;
	}

	protected Point2D[] intersectBoundingBox(BreakPoint b) {
		Point2D[] result = null;
		Line top = new Line(0, boundingBox.getY());
		Line bottom = new Line(0, boundingBox.getY() - boundingBox.getHeight());
		Line left = new Line(Double.POSITIVE_INFINITY, boundingBox.getX());
		Line right = new Line(Double.POSITIVE_INFINITY, boundingBox.getX()
				+ boundingBox.getWidth());
		Line bisector = b.getBisector();
		Point2D[] ps = new Point2D.Double[4];
		ps[0] = top.intersect(bisector);
		ps[1] = bottom.intersect(bisector);
		ps[2] = left.intersect(bisector);
		ps[3] = right.intersect(bisector);
		Point2D low = null;
		Point2D high = null;
		for (int i = 0; i < ps.length; i++) {
			low = ((low != null && ps[i] != null && ps[i].getY() < low.getY())
					|| low == null ? ps[i] : low);
			high = ((high != null && ps[i] != null && ps[i].getY() > high
					.getY())
					|| high == null ? ps[i] : high);
		}
		result = new Point2D.Double[2];
		result[0] = low;
		result[1] = high;

		if (low.getY() > high.getY()) {
			System.out.println("low.y < high.y should hold");
		}
		return result;
	}

	public void fixHalfEdge(BreakPoint b) {
		Point2D[] intersections = intersectBoundingBox(b);
		HalfEdge e = b.getEdge();
		HalfEdge t = e.getTwin();

		if (e.getOrigin() == null && t.getOrigin() != null) {
			Vertex v = new Vertex();
			v.setPosition(intersections[1]);
			v.setIncidentEdge(e);
			e.setOrigin(v);
		} else if (e.getOrigin() == null && t.getOrigin() == null) {
			System.out.println("This edge should not have two null origins:b="
					+ b);
		} else if (e.getOrigin() != null && t.getOrigin() == null) {
			Vertex v = new Vertex();
			v.setPosition(intersections[0]);
			v.setIncidentEdge(t);
			t.setOrigin(v);
		}
	}

	public void cleanup() {
		Iterator<VoronoiNode> iter = beachline.iterator();
		/**while (iter.hasNext()) {
			BreakPoint b = (BreakPoint) iter.next().getPoint();
			HalfEdge e = b.getEdge();
			HalfEdge t = new HalfEdge();
			if (e.getOrigin() == null) {
				Vertex v = new Vertex();
				v.setPosition(b.getPosition());
				v.setIncidentEdge(e);
				e.setOrigin(v);
			}
			e.setTwin(t);
			t.setTwin(e);
			danglingEdges.add(b);
		}**/
		fixHalfEdges();
	}

	public void fixHalfEdges() {
		while (!danglingEdges.isEmpty()) {
			fixHalfEdge(danglingEdges.remove());
		}
	}

}

package rsmith;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class FortuneData {

	private PriorityQueue<SweepEvent> eventQueue;
	private NavigableSet<VoronoiNode> beachline;
	private NavigableSet<SitePoint> sites;
	private NavigableSet<Point2D> points;
	private double sweepY;

	/**
	 * @param p
	 */
	public FortuneData(NavigableSet<Point2D> p) {
		points = p;
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
	public NavigableSet<Point2D> getPoints() {
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

	public double getSweepY() {
		return sweepY;
	}

	public void setSweepY(double sweepY) {
		this.sweepY = sweepY;
	}
}

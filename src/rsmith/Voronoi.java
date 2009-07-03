package rsmith;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Voronoi {

	private PriorityQueue<SweepEvent> Q; // sort by y-value
	private TreeSet<Breakpoint> beachline; // sort by x-value
	private TreeSet<Site> sites;
	private TreeSet<Point2D> points;
	private double sweepY;

	/**
	 * @param p
	 */
	public Voronoi(TreeSet<Point2D> p) {
		points = p;
	}

	public void init() {
		Iterator<Point2D> iter = points.iterator();
		while (iter.hasNext()) {
			Point2D p = (Point2D) iter.next();
			Site s = new Site(p);
			sites.add(s);
			SiteEvent se = new SiteEvent(s);
			se.setVoronoi(this);
			Q.add(se);
		}
	}

	public void step() {
		if (!isFinished()) {
			SweepEvent e = (SweepEvent) Q.remove();
			if (e instanceof SiteEvent) {
				handleSiteEvent((SiteEvent) e);
			} else {
				handleCircleEvent((CircleEvent) e);
			}
		}
	}

	public boolean isFinished() {
		return Q.isEmpty();
	}

	public void handleSiteEvent(SiteEvent se) {
		if (beachline.isEmpty()) {
		} else {
			if (beachline.size() == 1) {

			} else {

			}
		}
	}

	public void handleCircleEvent(CircleEvent ce) {

	}

	/**
	 * @return the q
	 */
	/**
	 * @return
	 */
	public PriorityQueue<SweepEvent> getQ() {
		return Q;
	}

	/**
	 * @param q
	 *            the q to set
	 */
	/**
	 * @param q
	 */
	public void setQ(PriorityQueue<SweepEvent> q) {
		Q = q;
	}

	/**
	 * @return the breakpoints
	 */
	/**
	 * @return
	 */
	public TreeSet<Breakpoint> getBeachline() {
		return beachline;
	}

	/**
	 * @param breakpoints
	 *            the breakpoints to set
	 */
	/**
	 * @param breakpoints
	 */
	public void setBeachline(TreeSet<Breakpoint> breakpoints) {
		this.beachline = breakpoints;
	}

	/**
	 * @return the sites
	 */
	/**
	 * @return
	 */
	public TreeSet<Site> getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	/**
	 * @param sites
	 */
	public void setSites(TreeSet<Site> sites) {
		this.sites = sites;
	}

	/**
	 * @return the points
	 */
	/**
	 * @return
	 */
	public TreeSet<Point2D> getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	/**
	 * @param points
	 */
	public void setPoints(TreeSet<Point2D> points) {
		this.points = points;
	}

	public double getSweepY() {
		return sweepY;
	}

	public void setSweepY(double sweepY) {
		this.sweepY = sweepY;
	}
}

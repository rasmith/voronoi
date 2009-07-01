package rsmith;

import java.awt.geom.Point2D;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Voronoi {

	private PriorityQueue<SweepEvent> Q;
	private TreeSet<Breakpoint> breakpoints;
	private TreeSet<Site> sites;
	private TreeSet<Point2D> points;

	/**
	 * @param p
	 */
	public Voronoi(TreeSet<Point2D> p) {
		points = p;
	}

	/**
	 * 
	 */
	public void runVoronoi() {
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
	public TreeSet<Breakpoint> getBreakpoints() {
		return breakpoints;
	}

	/**
	 * @param breakpoints
	 *            the breakpoints to set
	 */
	/**
	 * @param breakpoints
	 */
	public void setBreakpoints(TreeSet<Breakpoint> breakpoints) {
		this.breakpoints = breakpoints;
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
}

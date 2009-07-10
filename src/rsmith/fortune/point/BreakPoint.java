package rsmith.fortune.point;

import java.awt.geom.Point2D;

import rsmith.geom.Quadratic;
import rsmith.util.PointUtils;

/**
 * @author agrippa
 * 
 */
public class BreakPoint extends AbstractPoint implements
		Comparable<BreakPoint>, VoronoiPoint {

	private BreakPoint previous;
	private BreakPoint next;
	private SitePoint left;
	private SitePoint right;
	private Point2D position = null;
	private double lastSweepY;

	/**
	 * @param previous
	 * @param next
	 * @param left
	 * @param right
	 */
	public BreakPoint(BreakPoint previous, BreakPoint next, SitePoint left,
			SitePoint right) {
		this.setPrevious(previous);
		this.setNext(next);
		this.left = left;
		this.right = right;
	}

	public BreakPoint() {
	}

	/**
	 * @param previous
	 */
	public void setPrevious(BreakPoint previous) {
		this.previous = previous;
	}

	/**
	 * @return the breakpoint that lies immediately to the left of this
	 *         breakpoint on the beachline
	 */
	public BreakPoint getPrevious() {
		return previous;
	}

	/**
	 * @param next
	 */
	public void setNext(BreakPoint next) {
		this.next = next;
	}

	/**
	 * @return the breakpoint immediately to the right of this breakpoint
	 */
	public BreakPoint getNext() {
		return next;
	}

	/**
	 * @return the site that generates the parabola on the beachline that lies
	 *         immediately to the right of this breakpoint
	 */
	public SitePoint getLeft() {
		return left;
	}

	/**
	 * @param left
	 */
	public void setLeft(SitePoint left) {
		this.left = left;
	}

	/**
	 * @return the site that generates the parabola on the beachline that lies
	 *         immediately to the right of this breakpoint
	 */
	public SitePoint getRight() {
		return right;
	}

	/**
	 * @param right
	 */
	public void setRight(SitePoint right) {
		this.right = right;
	}

	/**
	 * @return the current (x,y) coordinate of this breakpoint
	 */
	public Point2D getPosition() {
		double sweepY = getNode().getFortuneData().getSweepY();
		if (position == null || sweepY != lastSweepY) {
			lastSweepY = sweepY;
			updatePosition();
		}
		return position;
	}

	/**
	 * @param position
	 */
	private void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public int compareTo(BreakPoint bp) {
		return PointUtils.comparePointsX(this.getPosition(), bp.getPosition());
	}

	/**
	 * @param sweep
	 */
	private void updatePosition() {
		double sweep = lastSweepY;
		Point2D pos = calculatePosition(sweep);
		setPosition(pos);
	}

	public Point2D calculatePosition(double sweepY) {
		Point2D result = null;
		double sweep = sweepY;
		Quadratic qLeft = left.createQuadratic(sweep);
		Quadratic qRight = right.createQuadratic(sweep);
		double[] roots = qLeft.intersect(qRight);
		if (roots != null) {
			double x1 = roots[0];
			double x2 = roots[1];
			if (x1 != x2) {
				// (x1,y1) and (x2,y2) are the intersection points
				// intersect should return x1 and x2 such that x1 < x2
				// need to determine which parabola is lowest when x < x1
				double half = x1 - 20;
				double mid = (x1 + x2) / 2;
				if (qLeft.eval(half) < qRight.eval(half)
						&& qRight.eval(mid) < qLeft.eval(mid)) {
					double y1 = qLeft.eval(x1);
					result = new Point2D.Double(x1, y1);
				} else {
					double y2 = qLeft.eval(x2);
					result = new Point2D.Double(x2, y2);
				}
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BreakPoint))
			return false;
		BreakPoint other = (BreakPoint) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

}

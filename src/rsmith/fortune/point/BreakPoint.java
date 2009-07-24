package rsmith.fortune.point;

import java.awt.geom.Point2D;

import rsmith.geom.Line;
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
		super();
		this.setPrevious(previous);
		this.setNext(next);
		this.left = left;
		this.right = right;
	}

	public BreakPoint() {
		super();
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
		int result = 0;
		if(this == bp) {
			result = 0;
		} else
		if(bp.getNext() == this || this.getNext() == bp) {
			result = ( this.getNext() == bp ? -1 : 1);
		} else {
			result = PointUtils.comparePointsX(this.getPosition(), bp.getPosition());
		}
		return result;
	}
	
	public double  [] intersectLeft(double y) {
		return this.getLeft().createQuadratic(this.getNode().getFortuneData().getSweepY()).intersectLine(new Line(0,y));
	}
	
	public double [] intersectRight(double y) {
		return this.getRight().createQuadratic(this.getNode().getFortuneData().getSweepY()).intersectLine(new Line(0,y));
	}

	public SitePoint getSiteAtSweep() {
		double sweepY = this.getNode().getFortuneData().getSweepY();
		SitePoint result = null;
		if (this.getLeft().getPosition().getY() == sweepY) {
			result = this.getLeft();
		} else
		if (this.getRight().getPosition().getY() == sweepY) {
			result = this.getRight();
		}
		return result;
	}
	
	public boolean  hasSiteAtSweep() {
		SitePoint site = getSiteAtSweep();
		return (site != null);
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
		// System.out.println("calculatePosition:sweepY=" + sweepY);
		Point2D result = null;
		double sweep = sweepY;
		SitePoint p = getSiteAtSweep();
		if (p == null) {
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
			// System.out.println("calculatePosition:result=" + result +
			// ",roots="
			// + roots + ",qLeft=" + qLeft + ",qRight=" + qRight);
		} else {
			SitePoint q = (p == getLeft() ? getRight() : getLeft());
			// System.out.println("p="+p);
			double xval = p.getPosition().getX();
			double yval = q.createQuadratic(sweepY).eval(xval);
			result = new Point2D.Double(xval, yval);
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
	
	public String getType() {
		return "breakpoint";
	}

}

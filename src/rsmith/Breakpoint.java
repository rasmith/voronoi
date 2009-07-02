package rsmith;

import java.awt.geom.Point2D;

/**
 * @author agrippa
 * 
 */
public class Breakpoint implements Comparable<Breakpoint> {

	private Breakpoint previous;
	private Breakpoint next;
	private Site left;
	private Site right;
	private Point2D position;
	private Voronoi voronoi;

	/**
	 * @param previous
	 * @param next
	 * @param left
	 * @param right
	 */
	public Breakpoint(Breakpoint previous, Breakpoint next, Site left,
			Site right) {
		this.setPrevious(previous);
		this.setNext(next);
		this.left = left;
		this.right = right;
	}

	/**
	 * @param previous
	 */
	public void setPrevious(Breakpoint previous) {
		this.previous = previous;
	}

	/**
	 * @return
	 */
	public Breakpoint getPrevious() {
		return previous;
	}

	/**
	 * @param next
	 */
	public void setNext(Breakpoint next) {
		this.next = next;
	}

	/**
	 * @return
	 */
	public Breakpoint getNext() {
		return next;
	}

	/**
	 * @return
	 */
	public Site getLeft() {
		return left;
	}

	/**
	 * @param left
	 */
	public void setLeft(Site left) {
		this.left = left;
	}

	/**
	 * @return
	 */
	public Site getRight() {
		return right;
	}

	/**
	 * @param right
	 */
	public void setRight(Site right) {
		this.right = right;
	}

	/**
	 * @return
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	private void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public int compareTo(Breakpoint bp) {
		return PointUtils.comparePoints(this.getPosition(), bp.getPosition());
	}

	/**
	 * @param sweep
	 */
	private void updatePosition() {
		double sweep = voronoi.getSweepY();
		Quadratic qLeft = left.getQuadratic(sweep);
		Quadratic qRight = right.getQuadratic(sweep);
		double[] roots = qLeft.intersect(qRight);
		if (roots != null) {
			double x1 = roots[0];
			double x2 = roots[1];
			if (x1 != x2) {	
				// (x1,y1) and (x2,y2) are the intersection points
				// intersect should return x1 and x2 such that x1 < x2
				// need to determine which parabola is lowest when x < x1
				double half=x1/2;
				double mid=(x1+x2)/2;
				if(qLeft.eval(half) < qRight.eval(half) && qRight.eval(mid) < qLeft.eval(mid) ) {
					double y1=qLeft.eval(x1);
					setPosition(new Point2D.Double(x1,y1));
				} else {
					double y2=qLeft.eval(x2);
					setPosition(new Point2D.Double(x2,y2));
				}
			}
		}	
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
		if (!(obj instanceof Breakpoint))
			return false;
		Breakpoint other = (Breakpoint) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	public void setVoronoi(Voronoi voronoi) {
		this.voronoi = voronoi;
	}

	public Voronoi getVoronoi() {
		return voronoi;
	}

}

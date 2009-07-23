package rsmith.fortune.point;

import java.awt.geom.Point2D;

import rsmith.geom.Quadratic;
import rsmith.util.PointUtils;

/**
 * @author agrippa
 * 
 */
public class SitePoint extends AbstractPoint implements Comparable<SitePoint>,
		VoronoiPoint {

	private Point2D position;

	/**
	 * @param p
	 */
	public SitePoint(Point2D p) {
		super();
		position = p;
	}

	public SitePoint() {
		super();
	}

	/**
	 * @param sweep
	 * @return creates a quadratic w.r.t. the sweep at value sweep
	 */
	public Quadratic createQuadratic(double sweep) {
		double x = position.getX();
		double y = position.getY();
		double d = 2 * (y - sweep);
		double A = (1 / d);
		double B = -(2 * x) / d;
		double C = (x * x + y * y - sweep * sweep) / d;
		Quadratic q = new Quadratic(A, B, C);
		return q;
	}
	

	/**
	 * @return (x,y) coordinate of this site
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * @param p
	 */
	public void setPosition(Point2D p) {
		this.position = p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SitePoint other = (SitePoint) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public int compareTo(SitePoint o) {
		return PointUtils.comparePointsX(this.getPosition(), o.getPosition());
	}
	
	
	public String getType() {
		return "site";
	}
}

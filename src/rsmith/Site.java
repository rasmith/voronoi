package rsmith;

import java.awt.geom.Point2D;

/**
 * @author agrippa
 * 
 */
public class Site implements Comparable<Site> {

	private Point2D p;

	/**
	 * @param p
	 */
	public Site(Point2D p) {
	}

	/**
	 * @param sweep
	 * @return
	 */
	public Quadratic getQuadratic(double sweep) {
		double x = p.getX();
		double y = p.getY();
		double d = 2 * (y - sweep);
		double A = (1 / d);
		double B = -(2 * x) / d;
		double C = (x * x + y * y - sweep * sweep) / d;
		Quadratic q = new Quadratic(A, B, C);
		return q;
	}

	/**
	 * @return
	 */
	public Point2D getP() {
		return p;
	}

	/**
	 * @param p
	 */
	public void setP(Point2D p) {
		this.p = p;
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
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		Site other = (Site) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}

	@Override
	public int compareTo(Site o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

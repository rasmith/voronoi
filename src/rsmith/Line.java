package rsmith;

import java.awt.geom.Point2D;

public class Line {

	private double m;
	private double b;

	public Line(double m, double b) {
		this.m = m;
		this.b = b;
	}

	public static Line bisector(Point2D p, Point2D q) {
		Line result = null;
		double	px = p.getX(), py = p.getY(), 
				qx = q.getX(), qy = q.getY();
		double	diffx = px - qx;
		double 	diffy = py - qy;
		double m=0, b=0;
		if (diffx == 0 && diffy != 0) {
			// one point is above another
			// bisector is the horizontal line halfway between them
			m = 0; // horizontal lines have 0 slope;
			b = (py + qy) / 2;
		} else if (diffx != 0 && diffy == 0) {
			// one bisector is beside the other
			// bisector is the vertical line halfway between them
			m = Double.POSITIVE_INFINITY; // vertical lines have infinite slope
			b = (px + qx) / 2;
		} else 
		if(!(diffx == 0 && diffy == 0)){
			// ensure that p != q
			m = diffy / diffx;
			b = py  - m * px;
		}
		result = new Line(m,b);
		return result;
	}

	public boolean isVertical() {
		return getM() == Double.POSITIVE_INFINITY;
	}

	protected Point2D intersectVertical(Line v, Line nv) {
		// if nv is horizontal, and v holds x-value x always and nv holds
		// y-value y always, then (x,y) is the intersection and since
		// v.getB() returns x and eval(v.getB()) will return nv.getB()=y since
		// nv.getM()=0, intersectVertical will return (x,y)
		Point2D result = new Point2D.Double(v.getB(), eval(v.getB()));
		return result;
	}

	public Point2D intersect(Line l) {
		Point2D result = null;
		double diffm = this.getM() - l.getM();
		// if one is vertical, try to get intersection with the case of at least
		// one line being vertical
		if (l.isVertical() || this.isVertical()) {
			// if both lines are vertical, then treat as parallel and ignore
			// otherwise, can get intersection of the vertical line with the
			// non-vertical line
			// if one is vertical and one is horizontal then intersectVertical
			// should work
			if (!(l.isVertical() && this.isVertical())) {
				result = (l.isVertical() ? intersectVertical(l, this)
						: intersectVertical(this, l));
			}
		} else
		// if lines are parallel either there will be no intersection or
		// infinite intersection
		if (diffm != 0) {
			double diffb = l.getB() - this.getB();
			double x = diffb / diffm;
			double y = eval(x);
			result = new Point2D.Double(x, y);
		}
		return result;
	}

	public double eval(double x) {
		return m * x + b;
	}

	/**
	 * @return the m
	 */
	public double getM() {
		return m;
	}

	/**
	 * @param m
	 *            the m to set
	 */
	public void setM(double m) {
		this.m = m;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}

}

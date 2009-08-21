package rsmith.util;

import java.awt.geom.Point2D;

import rsmith.geom.Line;
import rsmith.util.NumberUtils;

public class PointUtils {
	
	public static Point2D ORIGIN = new Point2D.Double(0,0);
	
	public static int comparePointsX(Point2D p, Point2D q) {
		return comparePoints(p, q, true);
	}

	public static int comparePointsY(Point2D p, Point2D q) {
		return comparePoints(p, q, false);
	}

	public static int comparePoints(Point2D p, Point2D q, boolean xory) {
		double diffx = p.getX() - q.getX();
		double diffy = p.getY() - q.getY();
		double diff1 = (xory ? diffx : diffy);
		double diff2 = (xory ? diffy : diffx);
		int result = (diff1 < 0 ? -1 : (diff1 > 0 ? 1 : (diff2 < 0 ? -1
				: (diff2 > 0 ? 1 : 0))));
		return result;
	}

	public static Point2D randomPoint(double min, double max) {
		return new Point2D.Double(NumberUtils.randomNumber(min, max),
				NumberUtils.randomNumber(min, max));
	}
	
	public static Point2D subtract(Point2D p, Point2D q) {
		return new Point2D.Double(p.getX()-q.getX(),p.getY()-q.getY());
	}
	
	public static double left(Point2D p, Point2D q, Point2D r) {
		Point2D v = subtract(q,p);
		Point2D w = subtract(r,p);
		return cross(v,w);
	}
	
	public static boolean ccw(Line l, Point2D p) {
		Line k1 = Line.create(ORIGIN,p);
		Line k2 = l.perp(p);
		Point2D q1 = l.intersect(k1);
		Point2D q2 = l.intersect(k2);
		return ccw(q1,q2,p);
	}
	
	public static boolean ccw(Point2D p, Point2D q, Point2D r) {
		return NumberUtils.gt(left(p,q,r),0);
	}
	
	public static boolean cw(Point2D p, Point2D q, Point2D r) {
		return NumberUtils.lt( left(p,q,r),0);
	}
	
	public static boolean collinear(Point2D p, Point2D q, Point2D r) {
		return NumberUtils.eq(left(p,q,r),0);
	}
	
	public static double cross(Point2D p, Point2D q) {
		return (p.getX()*q.getY() - p.getY()*q.getX());
	}
	
}

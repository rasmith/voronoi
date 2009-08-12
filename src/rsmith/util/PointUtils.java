package rsmith.util;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class PointUtils {
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
	
	// Check to see if r is left of the segment pq.  
	public static boolean isLeft(Point2D p, Point2D q, Point2D r) {
		boolean result = false;
		Point2D v =new Point2D.Double(q.getX()-p.getX(), q.getY()-p.getY());
		Point2D w = new Point2D.Double(r.getX()-p.getX(),r.getY()-p.getY());
		double vdotw = v.getX()*w.getX()+v.getY()*w.getY();
		if(vdotw>0) {
			result=true;
		}
		return result;
	}
	
	
}

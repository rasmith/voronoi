package rsmith.util;

import java.awt.geom.Point2D;


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
	        return new Point2D.Double(NumberUtils.randomNumber(min,max),NumberUtils.randomNumber(min,max));
	    }
}

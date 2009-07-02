package rsmith;

import java.awt.geom.Point2D;

public class PointUtils {
	public static int comparePoints(Point2D p, Point2D q) {
		double diffx = p.getX() - q.getX();
		double diffy = p.getY() - q.getY();
		int result=	(diffx < 0 ? -1 : 
						(diffx > 0 ? 1 : 
							(diffy < 0 ? -1 :
								(diffy > 0 ? 1 : 0))));
		return result;
	}
}

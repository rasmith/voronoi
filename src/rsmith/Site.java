package rsmith;

import java.awt.geom.Point2D;

public class Site {
	Point2D p;
	
	public Site(Point2D p) {
	}
	
	public Quadratic getQuadratic(double sweep) {
		double x = p.getX();
		double y = p.getY();
		double d = 2*(y-sweep);
		double A = (1/d);
		double B =  -(2*x)/d;
		double C = (x*x+y*y-sweep*sweep)/d;
		Quadratic q = new Quadratic(A,B,C);
		return q;
	}
}

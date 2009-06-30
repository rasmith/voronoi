package rsmith;

import java.awt.geom.Point2D;

public class Breakpoint {
	
	private Breakpoint previous;
	private Breakpoint next;
	private Site left;
	private Site right;

	public void Breakpoint(	Breakpoint previous,
						Breakpoint next,
						Site left,
						Site right)  {
		this.previous = previous;
		this.next = next;
		this.left = left;
		this.right = right;
	}
	
	public Point2D getPosition(double sweep) {
		Quadratic qLeft = left.getQuadratic(sweep);
		Quadratic qRight = right.getQuadratic(sweep);
		double [] roots = qLeft.intersect(qRight);
		if(roots != null) {
			double x1 = roots[0];
			double x2 = roots[1];
			if(x1 != x2) {
			}		
		}
		return null;
	}
	
}

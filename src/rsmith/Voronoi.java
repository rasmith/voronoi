package rsmith;

import java.awt.geom.Point2D;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Voronoi {
	private PriorityQueue Q;
	private TreeSet<Breakpoint> breakpoints;
	private TreeSet<Site> sites;
	private TreeSet<Point2D> points;
	
	public Voronoi(TreeSet<Point2D> p) {
		points = p;
	}
	
	public void runVoronoi()  {
	}
}


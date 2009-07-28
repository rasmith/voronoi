package rsmith.fortune.point;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import rsmith.fortune.VoronoiNode;

public abstract class AbstractPoint implements VoronoiPoint {
	private VoronoiNode node = null;
	private static int curid = 0;
	private int id = 0;

	private static double xmax = Double.NEGATIVE_INFINITY;
	private static double ymax = Double.NEGATIVE_INFINITY;
	private static double xmin = Double.POSITIVE_INFINITY;
	private static double ymin = Double.POSITIVE_INFINITY;

	protected AbstractPoint() {
		id = curid;
		curid++;
	}

	public void setNode(VoronoiNode node) {
		this.node = node;
	}

	public VoronoiNode getNode() {
		return this.node;
	}

	public int getID() {
		return id;
	}

	protected abstract Point2D getCurrentPosition();

	public Point2D getPosition() {
		Point2D result = this.getCurrentPosition();
		xmax = Math.max(xmax, result.getX());
		ymax = Math.max(ymax, result.getY());
		xmin = Math.min(xmin, result.getX());
		ymin = Math.min(ymin, result.getY());
		return result;
	}

	public static Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(xmin, ymax, xmax - xmin, ymax - ymin);
	}

	public String toString() {
		return "[" + this.getPosition().getX() + "," + this.getType() + ","
				+ this.getID() + "]";
	}
}

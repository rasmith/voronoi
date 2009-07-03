package rsmith;

import java.awt.geom.Point2D;

public class VoronoiNode implements Comparable<VoronoiNode> {
	private VoronoiPoint point = null;

	public VoronoiNode(VoronoiPoint point) {
		this.point = point;
	}

	public Point2D getPosition() {
		return point.getPosition();
	}

	@Override
	public int compareTo(VoronoiNode vn) {
		Point2D p = this.getPosition();
		Point2D q = vn.getPosition();
		return PointUtils.comparePointsX(p, q);
	}
}

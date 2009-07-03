package rsmith;

import java.awt.geom.Point2D;

public class VoronoiNode implements Comparable<VoronoiNode> {
	private VoronoiPoint point = null;
	private SweepEvent event = null;
	
	public VoronoiNode(VoronoiPoint point, SweepEvent event) {
		this.point = point;
		this.event = event;
	}

	public Point2D getPosition() {
		return point.getPosition();
	}

	public int compareTo(VoronoiNode vn) {
		Point2D p = this.getPosition();
		Point2D q = vn.getPosition();
		return PointUtils.comparePointsX(p, q);
	}

    public void setEvent(SweepEvent event) {
        this.event = event;
    }

    public SweepEvent getEvent() {
        return event;
    }

    public VoronoiPoint getPoint() {
        return point;
    }

    public void setPoint(VoronoiPoint point) {
        this.point = point;
    }
}

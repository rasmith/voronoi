package rsmith.fortune;

import java.awt.geom.Point2D;

import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.point.VoronoiPoint;
import rsmith.util.PointUtils;

public class VoronoiNode implements Comparable<VoronoiNode> {
	private VoronoiPoint point = null;
	private CircleEvent circleEvent = null;
	private FortuneData fortuneData = null;

	public VoronoiNode(VoronoiPoint point, CircleEvent event) {
		this.point = point;
		this.circleEvent = event;
	}

	public Point2D getPosition() {
		return point.getPosition();
	}

	public int compareTo(VoronoiNode vn) {
		Point2D p = this.getPosition();
		Point2D q = vn.getPosition();
		return PointUtils.comparePointsX(p, q);
	}

	public void setCircleEvent(CircleEvent event) {
		this.circleEvent = event;
	}

	public CircleEvent getCircleEvent() {
		return circleEvent;
	}

	public VoronoiPoint getPoint() {
		return point;
	}

	public void setPoint(VoronoiPoint point) {
		this.point = point;
	}

	/**
	 * @return the voronoi
	 */
	public FortuneData getFortuneData() {
		return fortuneData;
	}

	/**
	 * @param voronoi
	 *            the voronoi to set
	 */
	public void setFortuneData(FortuneData voronoi) {
		this.fortuneData = voronoi;
	}

}

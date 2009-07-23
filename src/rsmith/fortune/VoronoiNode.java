package rsmith.fortune;

import java.awt.geom.Point2D;

import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.fortune.point.VoronoiPoint;
import rsmith.util.PointUtils;

public class VoronoiNode implements Comparable<VoronoiNode> {
	private VoronoiPoint point = null;
	private CircleEvent circleEvent = null;
	private FortuneData fortuneData = null;

	public VoronoiNode(VoronoiPoint point, CircleEvent event, FortuneData data) {
		this.point = point;
		this.circleEvent = event;
		this.fortuneData = data;
	}

	public Point2D getPosition() {
		return point.getPosition();
	}

	public int compareTo(VoronoiNode vn) {
		int result = 0;
		VoronoiPoint vp = this.getPoint();
		VoronoiPoint vq = vn.getPoint();
		if(vp instanceof BreakPoint &&  vq instanceof BreakPoint) {
			result = ((BreakPoint)(vp)).compareTo((BreakPoint)vq);
		} else {
			Point2D p = vp.getPosition();
			Point2D q = vq.getPosition();
			result = PointUtils.comparePointsX(p, q);
		}
		return result;
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
	
	public String toString() {
		return getPoint().toString();
	}

}

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
		Point2D p = this.getPosition();
		Point2D q = vn.getPosition();
		int result = 0;
		// System.out.println( "VoronoiNode::compareTo:p="+p+",q="+q);
		if (this.point instanceof BreakPoint
				&& vn.getPoint() instanceof BreakPoint) {
			// System.out.println("VoronoiNode:compare two breakpoints");
			BreakPoint bp = (BreakPoint) getPoint();
			BreakPoint bq = (BreakPoint) vn.getPoint();
			result = bp.compareTo(bq);
		} else if (this.point instanceof SitePoint
				&& vn.getPoint() instanceof SitePoint) {
			// System.out.println("VoronoiNode:compare to site points");
			result = PointUtils.comparePointsX(p, q);
		} else {
			// System.out.println("VoronoiNode:compare one site point and one
			// breakpoint");
			boolean which = getPoint() instanceof BreakPoint;
			BreakPoint b = (which ? (BreakPoint) getPoint() : (BreakPoint) vn
					.getPoint());
			SitePoint s = (which ? (SitePoint) vn.getPoint()
					: (SitePoint) getPoint());
			double sweepY = this.getFortuneData().getSweepY();
			if (b.getLeft().getPosition().getY() == sweepY
					|| b.getRight().getPosition().getY() == sweepY) {
				p = (b.getLeft().getPosition().getY() == sweepY ? b.getLeft()
						.getPosition() : b.getRight().getPosition());
				q = s.getPosition();
				result = PointUtils.comparePointsX(p, q);
			} else {
				result = PointUtils.comparePointsX(p, q);
			}
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

}

package rsmith.fortune.event;

import java.awt.geom.Point2D;

import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.geom.Line;

public class CircleEvent extends AbstractSweepEvent implements SweepEvent {

	private Point2D eventPoint;
	private double eventY;
	private Point2D center;
	private double radius;
	private BreakPoint leftBP;
	private BreakPoint rightBP;
	private SitePoint pi;
	private SitePoint pj;
	private SitePoint pk;
	private static String eventType = "circle";

	public CircleEvent(Point2D center, double radius, SitePoint pi,
			SitePoint pj, SitePoint pk) {
		this.center = center;
		this.pi = pi;
		this.pj = pj;
		this.pk = pk;
		this.radius = radius;
		this.eventY = center.getY() - radius;
		this.eventPoint = new Point2D.Double(center.getX(), eventY);
	}

	public double getEventY() {
		return eventY;
	}

	public static Point2D computeCircle(Point2D pi, Point2D pj, Point2D pk) {
		Point2D result = null;
		if (pi.getX() <= pj.getX() && pj.getX() <= pk.getX()) {
			Line l1 = Line.bisector(pi, pj);
			Line l2 = Line.bisector(pj, pk);
			result = l1.intersect(l2);
		}
		return result;
	}

	public static CircleEvent createCircleEvent(double sweep, SitePoint pi,
			SitePoint pj, SitePoint pk) {
		CircleEvent result = null;
		Point2D center = computeCircle(pi.getPosition(), pj.getPosition(), pk
				.getPosition());
		if (center != null) {
			double radius = center.distance(pi.getPosition());
			if (center.getY() - radius <= sweep) {
				result = new CircleEvent(center, radius, pi, pj, pk);
			}
		}
		return result;
	}

	/**
	 * @return the pi
	 */
	public SitePoint getPi() {
		return pi;
	}

	/**
	 * @param pi
	 *            the pi to set
	 */
	public void setPi(SitePoint pi) {
		this.pi = pi;
	}

	/**
	 * @return the pj
	 */
	public SitePoint getPj() {
		return pj;
	}

	/**
	 * @param pj
	 *            the pj to set
	 */
	public void setPj(SitePoint pj) {
		this.pj = pj;
	}

	/**
	 * @return the pk
	 */
	public SitePoint getPk() {
		return pk;
	}

	/**
	 * @param pk
	 *            the pk to set
	 */
	public void setPk(SitePoint pk) {
		this.pk = pk;
	}

	/**
	 * @return the leftBP
	 */
	public BreakPoint getLeftBP() {
		return leftBP;
	}

	/**
	 * @param leftBP
	 *            the leftBP to set
	 */
	public void setLeftBP(BreakPoint leftBP) {
		this.leftBP = leftBP;
	}

	/**
	 * @return the rightBP
	 */
	public BreakPoint getRightBP() {
		return rightBP;
	}

	/**
	 * @param rightBP
	 *            the rightBP to set
	 */
	public void setRightBP(BreakPoint rightBP) {
		this.rightBP = rightBP;
	}

	/**
	 * @return the eventPoint
	 */
	public Point2D getEventPoint() {
		return eventPoint;
	}

	/**
	 * @param eventPoint
	 *            the eventPoint to set
	 */
	public void setEventPoint(Point2D eventPoint) {
		this.eventPoint = eventPoint;
	}

	/**
	 * @return the center
	 */
	public Point2D getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            the center to set
	 */
	public void setCenter(Point2D center) {
		this.center = center;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public String getEventType() {
		return eventType;
	}
}

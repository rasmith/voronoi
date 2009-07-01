package rsmith;

import java.awt.geom.Point2D;

public class CircleEvent implements SweepEvent {

	private Point2D eventPoint;
	private Point2D center;
	private Breakpoint leftBP;
	private Breakpoint rightBP;
	private Site pi;
	private Site pj;
	private Site pk;
	
	@Override
	public double getY() {
		return eventPoint.getY();
	}

	/**
	 * @return the pi
	 */
	public Site getPi() {
		return pi;
	}

	/**
	 * @param pi the pi to set
	 */
	public void setPi(Site pi) {
		this.pi = pi;
	}

	/**
	 * @return the pj
	 */
	public Site getPj() {
		return pj;
	}

	/**
	 * @param pj the pj to set
	 */
	public void setPj(Site pj) {
		this.pj = pj;
	}

	/**
	 * @return the pk
	 */
	public Site getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Site pk) {
		this.pk = pk;
	}

	/**
	 * @return the leftBP
	 */
	public Breakpoint getLeftBP() {
		return leftBP;
	}

	/**
	 * @param leftBP the leftBP to set
	 */
	public void setLeftBP(Breakpoint leftBP) {
		this.leftBP = leftBP;
	}

	/**
	 * @return the rightBP
	 */
	public Breakpoint getRightBP() {
		return rightBP;
	}

	/**
	 * @param rightBP the rightBP to set
	 */
	public void setRightBP(Breakpoint rightBP) {
		this.rightBP = rightBP;
	}

	/**
	 * @return the eventPoint
	 */
	public Point2D getEventPoint() {
		return eventPoint;
	}

	/**
	 * @param eventPoint the eventPoint to set
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
	 * @param center the center to set
	 */
	public void setCenter(Point2D center) {
		this.center = center;
	}

}

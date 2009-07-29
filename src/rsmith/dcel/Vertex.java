package rsmith.dcel;

import java.awt.geom.Point2D;

public class Vertex {
	private Point2D position = null;
	private HalfEdge incidentEdge = null;

	/**
	 * @return
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	/**
	 * @return
	 */
	public HalfEdge getIncidentEdge() {
		return incidentEdge;
	}

	/**
	 * @param incidentEdge
	 */
	public void setIncidentEdge(HalfEdge incidentEdge) {
		this.incidentEdge = incidentEdge;
	}
}

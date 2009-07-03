package rsmith;

import java.awt.geom.Point2D;

public interface VoronoiPoint {
	public Point2D getPosition();

	public void setNode(VoronoiNode node);

	public VoronoiNode getNode();
}

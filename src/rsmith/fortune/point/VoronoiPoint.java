package rsmith.fortune.point;

import java.awt.geom.Point2D;

import rsmith.fortune.VoronoiNode;

public interface VoronoiPoint {
	public Point2D getPosition();

	public void setNode(VoronoiNode node);

	public VoronoiNode getNode();
}

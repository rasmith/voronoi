package rsmith.fortune.point;

import rsmith.fortune.VoronoiNode;

public abstract class AbstractPoint implements VoronoiPoint {
	private VoronoiNode node = null;

	public void setNode(VoronoiNode node) {
		this.node = node;
	}

	public VoronoiNode getNode() {
		return this.node;
	}
}

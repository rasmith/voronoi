package rsmith.fortune.point;

import rsmith.fortune.VoronoiNode;

public abstract class AbstractPoint implements VoronoiPoint {
	private VoronoiNode node = null;
	private static int curid = 0;
	private int id = 0;
	protected AbstractPoint() {
		id=curid;
		curid++;
	}
	public void setNode(VoronoiNode node) {
		this.node = node;
	}

	public VoronoiNode getNode() {
		return this.node;
	}
	
	public int getID() {
		return id;
	}
	
	public String toString() {
		return "["+this.getPosition().getX()+","+this.getType()+","+this.getID()+"]";
	}
}

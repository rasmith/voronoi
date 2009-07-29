package rsmith.dcel;

import java.util.ArrayList;
import java.util.List;

public class DCEL {
	List<HalfEdge> edges = null;

	public DCEL() {
		edges = new ArrayList<HalfEdge>();
	}

	public List<HalfEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<HalfEdge> edges) {
		this.edges = edges;
	}
}

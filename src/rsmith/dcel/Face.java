package rsmith.dcel;

import java.util.List;

public class Face {
	private HalfEdge outerComponent = null;
	private List<HalfEdge> innerComponents = null;

	/**
	 * @return
	 */
	public HalfEdge getOuterComponent() {
		return outerComponent;
	}

	/**
	 * @param outerComponent
	 */
	public void setOuterComponent(HalfEdge outerComponent) {
		this.outerComponent = outerComponent;
	}

	/**
	 * @return
	 */
	public List<HalfEdge> getInnerComponents() {
		return innerComponents;
	}

	/**
	 * @param innerComponents
	 */
	public void setInnerComponents(List<HalfEdge> innerComponents) {
		this.innerComponents = innerComponents;
	}
}

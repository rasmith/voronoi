package rsmith.dcel;

public class HalfEdge {
	private Vertex origin = null;
	private HalfEdge twin = null;
	private HalfEdge next = null;
	private HalfEdge previous = null;

	/**
	 * @return
	 */
	public Vertex getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 */
	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}

	/**
	 * @return
	 */
	public HalfEdge getTwin() {
		return twin;
	}

	/**
	 * @param twin
	 */
	public void setTwin(HalfEdge twin) {
		this.twin = twin;
	}

	/**
	 * @return
	 */
	public HalfEdge getNext() {
		return next;
	}

	/**
	 * @param next
	 */
	public void setNext(HalfEdge next) {
		this.next = next;
	}

	/**
	 * @return
	 */
	public HalfEdge getPrevious() {
		return previous;
	}

	/**
	 * @param previous
	 */
	public void setPrevious(HalfEdge previous) {
		this.previous = previous;
	}
}

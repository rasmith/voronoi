package rsmith;

import java.awt.geom.Point2D;

/**
 * @author agrippa
 * 
 */
public class Breakpoint implements Comparable<Breakpoint> {

	private Breakpoint previous;
	private Breakpoint next;
	private Site left;
	private Site right;
	private Point2D position;

	/**
	 * @param previous
	 * @param next
	 * @param left
	 * @param right
	 */
	public Breakpoint(Breakpoint previous, Breakpoint next, Site left,
			Site right) {
		this.setPrevious(previous);
		this.setNext(next);
		this.left = left;
		this.right = right;
	}

	/**
	 * @param sweep
	 * @return
	 */
	public Point2D getPosition(double sweep) {
		Quadratic qLeft = left.getQuadratic(sweep);
		Quadratic qRight = right.getQuadratic(sweep);
		double[] roots = qLeft.intersect(qRight);
		if (roots != null) {
			double x1 = roots[0];
			double x2 = roots[1];
			if (x1 != x2) {
			}
		}
		return null;
	}

	/**
	 * @param previous
	 */
	public void setPrevious(Breakpoint previous) {
		this.previous = previous;
	}

	/**
	 * @return
	 */
	public Breakpoint getPrevious() {
		return previous;
	}

	/**
	 * @param next
	 */
	public void setNext(Breakpoint next) {
		this.next = next;
	}

	/**
	 * @return
	 */
	public Breakpoint getNext() {
		return next;
	}

	/**
	 * @return
	 */
	public Site getLeft() {
		return left;
	}

	/**
	 * @param left
	 */
	public void setLeft(Site left) {
		this.left = left;
	}

	/**
	 * @return
	 */
	public Site getRight() {
		return right;
	}

	/**
	 * @param right
	 */
	public void setRight(Site right) {
		this.right = right;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((previous == null) ? 0 : previous.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Breakpoint))
			return false;
		Breakpoint other = (Breakpoint) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (previous == null) {
			if (other.previous != null)
				return false;
		} else if (!previous.equals(other.previous))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public int compareTo(Breakpoint arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}

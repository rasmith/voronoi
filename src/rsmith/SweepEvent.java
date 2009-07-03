package rsmith;

public interface SweepEvent extends Comparable<SweepEvent> {
	public double getY();

	public void setVoronoi(Voronoi v);
}

package rsmith;

public abstract class AbstractSweepEvent implements SweepEvent {
	private Voronoi voronoi;

	public int compareTo(SweepEvent se) {
		double y1 = getY();
		double y2 = se.getY();
		return (y1 < y2 ? -1 : (y1 > y2 ? 1 : 0));
	}

	public Voronoi getVoronoi() {
		return voronoi;
	}

	public void setVoronoi(Voronoi voronoi) {
		this.voronoi = voronoi;
	}
}

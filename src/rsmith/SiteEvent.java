package rsmith;

public class SiteEvent implements SweepEvent {

	private Site site;
	private Voronoi voronoi;
	
	@Override
	public double getY() {
		return site.getPosition().getY();
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Voronoi getVoronoi() {
		return voronoi;
	}

	public void setVoronoi(Voronoi voronoi) {
		this.voronoi = voronoi;
	}

	
}

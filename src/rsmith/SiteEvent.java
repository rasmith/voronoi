package rsmith;

public class SiteEvent implements SweepEvent {

	private Site site;
	
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
	
}

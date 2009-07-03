package rsmith;

public class SiteEvent extends AbstractSweepEvent implements SweepEvent {

	private Site site;

	public SiteEvent(Site s) {
		site = s;
	}

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

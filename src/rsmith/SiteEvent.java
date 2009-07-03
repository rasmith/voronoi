package rsmith;

public class SiteEvent extends AbstractSweepEvent implements SweepEvent {

	private SitePoint site;

	public SiteEvent(SitePoint s) {
		site = s;
	}

	public double getEventY() {
		return site.getPosition().getY();
	}

	public SitePoint getSite() {
		return site;
	}

	public void setSite(SitePoint site) {
		this.site = site;
	}

}

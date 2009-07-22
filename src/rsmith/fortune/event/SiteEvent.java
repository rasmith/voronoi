package rsmith.fortune.event;

import rsmith.fortune.point.SitePoint;

public class SiteEvent extends AbstractSweepEvent implements SweepEvent {

	private SitePoint site;
	private static String eventType = "site";

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

	public String getEventType() {
		return eventType;
	}

}

package rsmith.fortune.event;

import rsmith.fortune.FortuneData;

public abstract class AbstractSweepEvent implements SweepEvent {
	private FortuneData fortuneData;

	public int compareTo(SweepEvent se) {
		double y1 = getEventY();
		double y2 = se.getEventY();
		return (y1 < y2 ? 1 : (y1 > y2 ? -1 : 0));
	}

	public FortuneData getFortuneData() {
		return fortuneData;
	}

	public void setFortuneData(FortuneData fortuneData) {
		this.fortuneData = fortuneData;
	}

	public String toString() {
		return "[y=" + getEventY() + ",type=" + getEventType() + "]";
	}
}

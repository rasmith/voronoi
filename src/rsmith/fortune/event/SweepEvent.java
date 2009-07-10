package rsmith.fortune.event;

import rsmith.fortune.FortuneData;

public interface SweepEvent extends Comparable<SweepEvent> {
	public double getEventY();

	public void setFortuneData(FortuneData v);
}

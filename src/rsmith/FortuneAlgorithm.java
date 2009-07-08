package rsmith;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.NavigableSet;

public class FortuneAlgorithm {
	private FortuneData fortuneData = null;
	
	public FortuneAlgorithm(NavigableSet<Point2D> points) {
		fortuneData = new FortuneData(points);
	}
	
	public void init() {
		Iterator<Point2D> iter = getFortuneData().getPoints().iterator();
		while (iter.hasNext()) {
			Point2D p = (Point2D) iter.next();
			insertSitePoint(p);
		}
	}

	public void step() {
		if (!isFinished()) {
			SweepEvent e = (SweepEvent) getFortuneData().getEventQueue().remove();
			getFortuneData().setSweepY(e.getEventY());
			if (e instanceof SiteEvent) {
				handleSiteEvent((SiteEvent) e);
			} else {
				handleCircleEvent((CircleEvent) e);
			}
		}
	}

	public boolean isFinished() {
		return getFortuneData().getEventQueue().isEmpty();
	}

	public void handleSiteEvent(SiteEvent se) {
		FortuneData data = getFortuneData();
		NavigableSet<VoronoiNode> beachline = data.getBeachline();
		if (beachline.isEmpty()) {
			beachline.add(new VoronoiNode(se.getSite(), se));
			return;
		}
		SitePoint p = se.getSite();
		BreakPoint bl = null;
		BreakPoint br = null;
		VoronoiNode vp = new VoronoiNode(p, null);
		SitePoint q = null;
		// find arc above the site p
		if (getFortuneData().getBeachline().size() == 1) {
			// if there is only one element in the tree
			// then just remove it and get its point q
			VoronoiNode vn = beachline.first();
			beachline.remove(vn);
			q = (SitePoint) vn.getPoint();
		} else {
			// if there is more than one element then
			// get the breakpoints bl and br that
			// lie immediately to the left and right of p
			VoronoiNode before = beachline.floor(vp); // this gets the nodes
			VoronoiNode after = beachline.ceiling(vp);
			// get the corresponding breakpoints
			bl = (BreakPoint) before.getPoint();
			br = (BreakPoint) after.getPoint();
			assert (!(bl == null && br == null));
			// if bl is null, then use br
			if (bl == null && br != null) {
				q = br.getLeft();
			} else {
				// if we get here we know that it is not the case that (bl ==
				// null && br == null)
				// is true, so bl cannot be null here since that means size=1
				// and we know that size>1
				// at this point, so there must be at least one breakpoint, or
				// at least it should not be the case
				// furthermore, if bl is not null, then either br is null or it
				// isn't.
				// if br is null then we just use bl.right, otherwise
				// we still use bl.right so in either case, b
				q = bl.getRight();
			}
		}

		// we should have q at this point
		assert (q != null);

		// create the two new breakpoints that will intersect this arc
		BreakPoint b1 = new BreakPoint();
		BreakPoint b2 = new BreakPoint();
		
		VoronoiNode vb1 = insertBreakPoint(b1,bl,b2,q,p);
		VoronoiNode vb2 = insertBreakPoint(b2,b1,br,p,q);
		
		fixCircleEvent(bl,br);
		
		if(beachline.size() > 2) {
			insertCircleEvents(vb1, vb2);
		}
	}

	private void insertSitePoint(Point2D p) {
		FortuneData data = getFortuneData();
		SitePoint s = new SitePoint(p);
		data.getSites().add(s);
		SiteEvent se = new SiteEvent(s);
		se.setFortuneData(data);
		data.getEventQueue().add(se);
	}
	
	private VoronoiNode insertBreakPoint(BreakPoint b, BreakPoint previous, BreakPoint next, SitePoint left, SitePoint right) {
		FortuneData data = getFortuneData();
		b.setPrevious(previous);
		b.setNext(next);
		b.setLeft(left);
		b.setRight(right);
		VoronoiNode node = new VoronoiNode(b,null);
		node.setFortuneData(data);
		b.setNode(node);
		data.getBeachline().add(node);
		return node;
	}
	
	private void insertCircleEvents(VoronoiNode vb1, VoronoiNode vb2) {
		// see if there is an arc before the one in between b1 and b2
		BreakPoint b1 = (BreakPoint) vb1.getPoint();
		BreakPoint b2 = (BreakPoint) vb2.getPoint();
		BreakPoint bl = b1.getPrevious();
		BreakPoint br = b1.getNext();
		if(bl != null) {
			insertCircleEvent(bl,b1);
		}
		if(br != null) {
			insertCircleEvent(b2, br);
		}
	}

	private void insertCircleEvent(BreakPoint left, BreakPoint right) {
	    if(left == null || right == null) {
	        return;
	    }
		FortuneData data = getFortuneData();
		CircleEvent c = CircleEvent.createCircleEvent(data.getSweepY(), left.getLeft(), left.getRight(), right.getRight());
		c.setLeftBP(left);
		c.setRightBP(right);
		left.getNode().setEvent(c);
		data.getEventQueue().add(c);
	}
	
	private void fixCircleEvent(BreakPoint left, BreakPoint right) {
		if(left != null && right != null) {
			VoronoiNode node = left.getNode();
			CircleEvent event = (CircleEvent)node.getEvent();
			node.setEvent(null);
			node.getFortuneData().getEventQueue().remove(event);
		}
	}
	
	public void handleCircleEvent(CircleEvent ce) {
	    FortuneData data = this.getFortuneData();
	    NavigableSet<VoronoiNode> beachline = data.getBeachline();
		BreakPoint leftBP = ce.getLeftBP();
		BreakPoint rightBP = ce.getRightBP();
		BreakPoint previous = leftBP.getPrevious();
		BreakPoint next = rightBP.getNext();
		SitePoint left = leftBP.getLeft();
		SitePoint right = rightBP.getRight();
		beachline.remove(leftBP);
		beachline.remove(rightBP);
		BreakPoint b = new BreakPoint();
		this.insertBreakPoint(b, previous, next, left, right);
		
		insertCircleEvent(previous,b);
		insertCircleEvent(b,next);
	}
	
	/**
	 * @return the fortuneData
	 */
	public FortuneData getFortuneData() {
		return fortuneData;
	}
	/**
	 * @param fortuneData the fortuneData to set
	 */
	public void setFortuneData(FortuneData fortuneData) {
		this.fortuneData = fortuneData;
	}
	
	
}

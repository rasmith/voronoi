package rsmith.fortune;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.event.SiteEvent;
import rsmith.fortune.event.SweepEvent;
import rsmith.fortune.point.*;
import rsmith.geom.Quadratic;

public class FortuneAlgorithm {
	private FortuneData fortuneData = null;

	public FortuneAlgorithm(Set<Point2D> points) {
		fortuneData = new FortuneData(points);
	}

	public void init() {
		fortuneData.init();
		Iterator<Point2D> iter = getFortuneData().getPoints().iterator();
		while (iter.hasNext()) {
			Point2D p = (Point2D) iter.next();
			fortuneData.insertSitePoint(p);
		}
	}

	public void step() {
		if (!isFinished()) {
			// System.out.println("eventq="+getFortuneData().getEventQueue());
			// System.out.println("beachline="+getFortuneData().getBeachline());
			SweepEvent e = (SweepEvent) getFortuneData().getEventQueue()
					.remove();
			getFortuneData().setSweepY(e.getEventY());
			getFortuneData().setCurrentEvent(e);
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

	private static int vernum = 0;

	public void verifyBeachline() {
		vernum++;
		// System.out.println("verifyBeachline:vernum="+vernum);
		FortuneData data = getFortuneData();
		Iterator<VoronoiNode> iter = data.getBeachline().iterator();
		VoronoiNode current = null;
		// System.out.print("[");
		while (iter.hasNext()) {
			current = iter.next();
			if (current.getPoint() instanceof BreakPoint) {
				BreakPoint bp = (BreakPoint) current.getPoint();
				BreakPoint bpn = bp.getNext();
				if (bpn != null
						&& bpn.getPosition().getX() < bp.getPosition().getX()) {
					// System.out.println("Found breakpoints out of order
					// (bp,bpn):"+bp.getPosition()+","+bpn.getPosition());
				}
				if (bpn != null && bp.getRight() != bpn.getLeft()) {
					// System.out.println("bp.getRight() == bpn.getLeft() should
					// hold at all times");
				}
				BreakPoint bpp = bp.getPrevious();
				if (bpp != null
						&& bpp.getPosition().getX() > bp.getPosition().getX()) {
					// System.out.println("Found breakpoints out of order
					// (bp,bpp)"+
					// bp.getPosition() + "," + bpp.getPosition());
				}
			} else {
				if (data.getBeachline().size() > 1) {
					// System.out.println("Found a site point when should not
					// have.");
				}
			}

		}
		// System.out.println("]");
		Iterator<SitePoint> piter = data.getSites().iterator();
		while (piter.hasNext()) {
			SitePoint p = piter.next();
			Point2D pos = p.getPosition();
			if (p.isProcessed() && pos.getY() != data.getSweepY()) {
				Arc a = data.findArcAbove(p);
				SitePoint q = a.getSite();
				Quadratic f = q.createQuadratic(this.getFortuneData()
						.getSweepY());
				double yval = f.eval(pos.getX());
				if (yval > pos.getY()) {
					System.out.println("Processed point,p=(" + pos.getX() + ","
							+ pos.getY()
							+ "), should be behind beachline, ybeach=" + yval
							+ ".");
				}
			}

		}
	}

	public void handleSiteEvent(SiteEvent se) {
		System.out.println("handleSiteEvent:y=" + fortuneData.getSweepY());
		NavigableSet<VoronoiNode> beachline = fortuneData.getBeachline();

		SitePoint p = se.getSite();
		if (beachline.isEmpty()) {
			beachline.add(new VoronoiNode(p, null, this.getFortuneData()));
		} else {
			BreakPoint bl = null;
			BreakPoint br = null;
			SitePoint q = null;
			// find arc above the site p
			if (getFortuneData().getBeachline().size() == 1) {
				// if there is only one element in the tree
				// then just remove it and get its point q
				VoronoiNode vn = fortuneData.getBeachline().first();
				fortuneData.getBeachline().remove(vn);
				q = (SitePoint) vn.getPoint();
			} else {
				Arc arc = getFortuneData().findArcAbove(p);

				bl = arc.getLeft();
				br = arc.getRight();
				q = arc.getSite();
			}

			// if bl has a circle event, then its a false alarm, so must be
			// removed
			if (bl != null && bl.getNode().getCircleEvent() != null) {
				fortuneData.getEventQueue().remove(
						bl.getNode().getCircleEvent());
			}

			// create the two new breakpoints that will intersect this arc
			BreakPoint b1 = new BreakPoint();
			BreakPoint b2 = new BreakPoint();

			VoronoiNode vb1 = fortuneData.insertBreakPoint(b1, bl, b2, q, p);
			VoronoiNode vb2 = fortuneData.insertBreakPoint(b2, b1, br, p, q);
			
			// insert half edges
			fortuneData.insertHalfEdge(b1);
			fortuneData.insertHalfEdge(b2);

			verifyBeachline();
			
			// insert any circle events
			if (beachline.size() > 2) {
				fortuneData.insertCircleEvents(vb1, vb2);
			}
			
			verifyBeachline();
		}
		p.setProcessed(true);
	}

	public void handleCircleEvent(CircleEvent ce) {
		System.out.println("handleCircleEvent:y=" + fortuneData.getSweepY());

		// System.out.println("handleCircleEvent:y="+fortuneData.getSweepY()+"-----------------");
		// the breakpoints representing the disappearing arc
		BreakPoint leftBP = ce.getLeftBP();
		BreakPoint rightBP = ce.getRightBP();
		// System.out.println("leftBP.id="+leftBP.getID()+",rightBP.id="+rightBP.getID());

		// the breakpoints that lie to the left and right of this arc
		BreakPoint previous = leftBP.getPrevious();
		BreakPoint next = rightBP.getNext();
		// System.out.println("previous.id="+(previous!=null?previous.getID():null)+",next.id="+(next!=null?next.getID():null));
		// the sites that generate the arcs lying to the left and right
		// of the disappearing arc
		SitePoint left = leftBP.getLeft();
		SitePoint right = rightBP.getRight();

		double eps = 1E-6;
		double delta = leftBP.getPosition().distance(rightBP.getPosition());
		if (delta < eps) {			
			// effectively remove this arc
			fortuneData.removeBreakPoint(leftBP);
			fortuneData.removeBreakPoint(rightBP);

			// check to see if the current arc has any other current circle
			// events
			// and remove those
			fortuneData.clearCircleEvent(previous);
			fortuneData.clearCircleEvent(rightBP);

			// insert new breakpoint to replace the two dissappearing ones
			BreakPoint b = new BreakPoint();
			fortuneData.insertBreakPoint(b, previous, next, left, right);
			
			// insert the two new circle events
			fortuneData.insertCircleEvent(previous, b);
			fortuneData.insertCircleEvent(b, next);
			
			// update the DCEL
			fortuneData.updateEdgeList(ce,b);
			
			verifyBeachline();
		} else {
			//System.out.println("leftBP=" + leftBP + ",rightBP=" + rightBP
			//		+ ",eps=" + eps + ",delta=" + delta);
		}
		// System.out.println("-------------------------------");
	}

	/**
	 * @return the fortuneData
	 */
	public FortuneData getFortuneData() {
		return fortuneData;
	}

	/**
	 * @param fortuneData
	 *            the fortuneData to set
	 */
	public void setFortuneData(FortuneData fortuneData) {
		this.fortuneData = fortuneData;
	}

}

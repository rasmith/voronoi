package rsmith;

import java.awt.geom.Point2D;

import rsmith.fortune.event.CircleEvent;
import rsmith.fortune.point.BreakPoint;
import rsmith.fortune.point.SitePoint;
import rsmith.geom.Line;
import rsmith.geom.Quadratic;
import rsmith.util.NumberUtils;
import rsmith.util.PointUtils;

public class Tests {
    private static double eps = 0.00005;
    public static void main(String [] args) {
        testBisector();
        testQuadratic();
        testCircleEvent();
        testSolveRoots();
        testBreakPoint();
    }
    public static void testCircleEvent() {
        System.out.println("testCircleEvent:begin");
        for(int i=0;i<100;i++) {
           Point2D pi = new Point2D.Double( NumberUtils.randomNumber(0,10), NumberUtils.randomNumber(0,1000));
           Point2D pj = new Point2D.Double( NumberUtils.randomNumber(11,20), NumberUtils.randomNumber(0,1000));
           Point2D pk = new Point2D.Double( NumberUtils.randomNumber(21,30), NumberUtils.randomNumber(0,1000));
           SitePoint si = new SitePoint(pi);
           SitePoint sj = new SitePoint(pj);
           SitePoint sk = new SitePoint(pk);
           double sweepY = NumberUtils.randomNumber(-1,-1000);
           CircleEvent event = CircleEvent.createCircleEvent(sweepY, si,sj,sk);
           if(event != null) {
               Point2D c = event.getCenter();
               double r = event.getRadius();
               if(r != c.distance(pi)) {
                   System.out.println("Radius inconsistency, r="+r+",c.distance(pi)="+c.distance(pi));
                   return;
               }
               Point2D e=event.getEventPoint();
               double d1=c.distance(pi);
               double d2=c.distance(pj);
               double d3=c.distance(pk);
               double d4=c.distance(e);
            
               if(    (d1-d2) > eps || (d1-d3) > eps || (d1-d4) > eps || (d2-d3) > eps
                       || (d2-d4) > eps || (d3-d4) > eps ) {
                   System.out.println("Distance inconsistency, d1="+d1+", d2="+d2+", d3="+d3+", d4="+d4);
                   return;
               }
           } else {
               Line l1 = Line.bisector(pi,pj);
               Line l2 = Line.bisector(pj, pk);
               Point2D c = l1.intersect(l2);
               double eventY = c.getY() - c.distance(pi);
               if(eventY <= sweepY) {
                   System.out.println("Event inconsistency: eventY="+eventY+", sweepY="+sweepY);
                   return;
               }  
               double x = c.getX();
               double y1 = l1.eval(x);
               double y2 = l2.eval(x);
               Point2D q1 = new Point2D.Double(x,y1);
               Point2D q2 = new Point2D.Double(x,y2);
               double d1 = q1.distance(q2);
           
               if(d1 > eps) {
                   System.out.println("Intersection inconsistency: d1="+d1);
                   return;
               }
           }
        }
        System.out.println("testCircleEvent:end");
    }
    public static void testSolveRoots() {
    	double a = Math.exp( NumberUtils.randomNumber(-10, 10 )) ;
    	double b = Math.exp( NumberUtils.randomNumber(-10,10));
    	double c = Math.exp( NumberUtils.randomNumber(-10,10));
    	Quadratic q = new Quadratic(a,b,c);
    	double [] roots = q.solve();
    	if(roots != null) {
    		double x1 = roots[0];
    		double x2 = roots[1];
    		double y1 = q.eval(x1);
    		double y2 = q.eval(x2);
    		if(Math.abs(y1) > eps) {
    			System.out.println("Solve failed, x1="+x1+",y1="+y1+",q="+q);
    			return;
    		}
    		if(Math.abs(y2) > eps) {
    			System.out.println("Solve failed,x2="+x2+",y2="+y2+",q="+q);
    			return;
    		}
    	}
    	
    }
    public static void testQuadratic() {
        System.out.println("testQuadratic:begin");
        for(int i=0;i<100;i++) {
            Point2D p = PointUtils.randomPoint(0,100);
            SitePoint s = new SitePoint(p);
            double sweepY = NumberUtils.randomNumber(-1,-1000);
            Quadratic f = s.createQuadratic(sweepY);
            for(int j=0;j<100;j++) {
                double x = NumberUtils.randomNumber(-1000,1000);
                double y = f.eval(x);
                Point2D l = new Point2D.Double(x,sweepY);
                Point2D q = new Point2D.Double(x,y);
                double d1 = q.distance(l);
                double d2 = q.distance(p);
               
                if(Math.abs(d1-d2)>eps) {
                    System.out.println("Quadratic distance check failed: q.distance(l)="+d1
                            +",q.distance(p)="+d2);
                    return;
                }
            }          
        }
        System.out.println("testQuadratic:end");
    }
    public static void testBisector() {
        System.out.println("testBisector:begin");
        for(int i=0;i<100;i++) {
            Point2D p = PointUtils.randomPoint(0,50);
            Point2D q = PointUtils.randomPoint(0,50);
            Line line = Line.bisector(p, q);
            for(int j=0;j<100;j++) {
                double x = NumberUtils.randomNumber(-1000,1000);
                double y = line.eval(x);
                Point2D r = new Point2D.Double(x,y);
                double d1 = r.distance(p);
                double d2 = r.distance(q);
               
                if(Math.abs(d1-d2) > eps) {
                    System.out.println("Bisector distance check failed: p="+p+",q="+q+", q.distance(p)="+d1
                            +",r.distance(q)="+d2+",line="+line);
                    return;
                }
            }
        }
        System.out.println("testBisector:end");
    }
    public static void testBreakPoint() {
    	System.out.println("testBreakPoint:begin");
    	for(int i=0;i<100;i++) {    		
    		Point2D p = PointUtils.randomPoint(500,1000);
    		Point2D q = PointUtils.randomPoint(0,400);
    		SitePoint sp = new SitePoint(p);
    		SitePoint sq = new SitePoint(q);
    		double sweepY = NumberUtils.randomNumber(-500,-1000);
    		BreakPoint b = new BreakPoint();
    		BreakPoint c = new BreakPoint();
    		Quadratic qp = sp.createQuadratic(sweepY);
    		Quadratic qq = sq.createQuadratic(sweepY);
    		
    		b.setLeft(sp);
    		b.setRight(sq);
    		
    		c.setLeft(sq);
    		c.setRight(sp);
    		
    		Point2D bpos = b.calculatePosition(sweepY);
    		Point2D cpos = c.calculatePosition(sweepY);
    		boolean error = false;
    		if(bpos.getX()  >= cpos.getX()) {
    			System.out.println("bpos.x < cpos.x should hold, bpos="+bpos+",cpos="+cpos
    					+"\n p="+p+",q="+q);
    			error=true;
    		} else {
    			double x1 = NumberUtils.randomNumber(bpos.getX() - 1000, bpos.getX() - eps);
    			double x2 = NumberUtils.randomNumber(bpos.getX() + eps, cpos.getX() - eps);
    			double x3 = NumberUtils.randomNumber(cpos.getX() + eps, cpos.getX() + 1000);
    			double y1p = qp.eval(x1);
    			double y1q = qq.eval(x1);
    			double y2p = qp.eval(x2);
    			double y2q = qq.eval(x2);
    			double y3p = qp.eval(x3);
    			double y3q = qq.eval(x3);
    			if(y1p >= y1q) {
    				System.out.println("y1p < y1q should hold: y1p="+y1p+",y1q="+y1q);
    				error=true;
    			}
    			if(y2q >= y2p) {
    				System.out.println("y2q < y2p should hold: y2q="+y2q+",y2p="+y2p);
    				error=true;
    			}
    			if(y3p >= y3q) {
    				System.out.println("y3p < y3q should hold:y3p="+y3p+",y3q="+y3q);
    				error=true;
    			}
    		}	
    		if(error) {
				return;
			}
    	}
    }
}

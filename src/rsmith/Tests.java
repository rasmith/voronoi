package rsmith;

import java.awt.geom.Point2D;

public class Tests {
    private static double eps = 0.00005;
    public static void main(String [] args) {
        testBisector();
        testQuadratic();
        testCircleEvent();
    }
    public static void testCircleEvent() {
        System.out.println("testCircleEvent:begin");
        for(int i=0;i<100;i++) {
           Point2D pi = new Point2D.Double( randomNumber(0,10), randomNumber(0,1000));
           Point2D pj = new Point2D.Double( randomNumber(11,20), randomNumber(0,1000));
           Point2D pk = new Point2D.Double( randomNumber(21,30), randomNumber(0,1000));
           SitePoint si = new SitePoint(pi);
           SitePoint sj = new SitePoint(pj);
           SitePoint sk = new SitePoint(pk);
           double sweepY = randomNumber(-1,-1000);
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
    public static void testQuadratic() {
        System.out.println("testQuadratic:begin");
        for(int i=0;i<100;i++) {
            Point2D p = randomPoint(0,100);
            SitePoint s = new SitePoint(p);
            double sweepY = randomNumber(-1,-1000);
            Quadratic f = s.createQuadratic(sweepY);
            for(int j=0;j<100;j++) {
                double x = randomNumber(-1000,1000);
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
            Point2D p = randomPoint(0,50);
            Point2D q = randomPoint(0,50);
            Line line = Line.bisector(p, q);
            for(int j=0;j<100;j++) {
                double x = randomNumber(-1000,1000);
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
    public static Point2D randomPoint(double min, double max) {
        return new Point2D.Double(randomNumber(min,max),randomNumber(min,max));
    }
    public static double randomNumber(double min, double max) {
        return Math.random()*(max-min)+min;
    }
}

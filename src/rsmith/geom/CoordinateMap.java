package rsmith.geom;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CoordinateMap {

	private Rectangle2D sourceRect = null;
	private Rectangle2D destRect = null;
	private Line xmap = null;
	private Line ymap = null;

	public CoordinateMap(Rectangle2D sourceRect, Rectangle2D destRect) {
		this.setDestRect(destRect);
		this.setSourceRect(sourceRect);
		init();
	}

	private void init() {
		xmap = createSegmentMap(sourceRect.getX(), sourceRect.getX()
				+ sourceRect.getWidth(), destRect.getX() + destRect.getWidth(),
				destRect.getX() );
		ymap = createSegmentMap(sourceRect.getY(), sourceRect.getY()
				- sourceRect.getHeight(),  destRect.getY(), destRect.getY()
				+ destRect.getHeight());
	}

	private Line createSegmentMap(double a, double b, double c, double d) {
		double M = (d - c) / (b - a);
		double B = c - M * a;
		return new Line(M, B);
	}

	public double mapx(double value) {
		return xmap.eval(value);
	}

	public double mapy(double value) {
		return ymap.eval(value);
	}

	public Point2D map(Point2D p) {
		return new Point2D.Double(mapx(p.getX()), mapy(p.getY()));
	}

	public Rectangle2D getSourceRect() {
		return sourceRect;
	}

	public void setSourceRect(Rectangle2D sourceRect) {
		this.sourceRect = sourceRect;
	}

	public Rectangle2D getDestRect() {
		return destRect;
	}

	public void setDestRect(Rectangle2D destRect) {
		this.destRect = destRect;
	}

	public Line getXmap() {
		return xmap;
	}

	public void setXmap(Line xmap) {
		this.xmap = xmap;
	}

	public Line getYmap() {
		return ymap;
	}

	public void setYmap(Line ymap) {
		this.ymap = ymap;
	}

}

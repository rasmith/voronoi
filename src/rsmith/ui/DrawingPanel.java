package rsmith.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import rsmith.fortune.FortuneAlgorithm;
import rsmith.util.PointUtils;

class DrawingPanel extends JPanel {

	private Set<Point2D> points = null;
	
    public DrawingPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));    
        init();
    }
    
    private void init() {
    	points = new HashSet<Point2D>();
    	for(int i=0;i<10;i++) {           	  
    		points.add(PointUtils.randomPoint(200, 500));
        }
    	FortuneAlgorithm fortune = new FortuneAlgorithm(points);
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }
    
    public void repaint() {
    	super.repaint();
    	System.out.println("repaint");
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent");
    }  
}
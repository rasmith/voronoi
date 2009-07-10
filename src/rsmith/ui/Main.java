package rsmith.ui;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Main");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add(new DrawingPanel());
        f.pack();
        f.setVisible(true);
    } 
}



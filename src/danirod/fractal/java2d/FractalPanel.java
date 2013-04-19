package danirod.fractal.java2d;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import danirod.fractal.Fractal;

/**
 * Panel for rendering fractals.
 * 
 * @author danirod
 */
public class FractalPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private Fractal f;
    
    public FractalPanel(Fractal f)
    {
        this.f = f;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        // Initialise some variables that we will use.
        Graphics2D gInstance = (Graphics2D) g;
        int width = getWidth(), height = getHeight();
        
        double[] data = f.getValues(); // actual data we'll be plotting
        float sep = (float) width / (data.length - 1); // point separation
        
        // Render all the points in groups of 2.
        Point pixel0 = new Point(), pixel1 = new Point();        
        for(int k = 0; k < data.length - 1; k++) {
            // Get pixel coordinates for this point.
            pixel0.x = (int) (sep * k);
            pixel0.y = (int) (height / 2 - (float) data[k] * height / 2);
            
            // Get pixel coordinates for the next point
            pixel1.x = (int) (sep * (k + 1));
            pixel1.y = (int) (height / 2 - (float) data[k+1] * height / 2);
            
            gInstance.drawLine(pixel0.x, pixel0.y, pixel1.x, pixel1.y);
        }
    }
}

package danirod.fractal.java2d;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import danirod.fractal.Fractal;

/**
 * Fractal frame. This is the window that will render the frame and the fractal.
 * 
 * @author danirod
 */
public class FractalFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private Fractal fractal;
    
    public FractalFrame(Fractal fractal)
    {
        this.fractal = fractal;
        init();
    }
    
    private void init()
    {
        // initialize window size and position
        setPreferredSize(new Dimension(640, 360));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dim.getWidth() - getWidth()) / 4);
        int y = (int) ((dim.getHeight() - getHeight()) / 4);
        // note that we just divided by 4, that makes the screen to center 
        setLocation(x, y);
        
        setTitle("Fractal Viewer");
        add(new FractalPanel(fractal)); // add the panel
        pack();
    }
}

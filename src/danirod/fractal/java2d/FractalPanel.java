/*
 * 2D Fractal Terrain Generator
 * Generates terrain using the random midpoint displacement algorhythm.
 * Copyright (C) 2013 Dani Rodríguez <danirod@outlook.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

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

import java.util.Random;

import javax.swing.WindowConstants;

import danirod.fractal.Fractal;

public class FractalAWT
{
    public static void main(String args[])
    {
        Fractal fractal = new Fractal();
        Random rnd = new Random();
        long seed = rnd.nextLong();
        fractal.init(seed, 0, 0);
        for(int i = 0; i < 8; i++)
            fractal.iterate();
        
        FractalFrame ff = new FractalFrame(fractal);
        ff.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ff.setVisible(true);
    }
}

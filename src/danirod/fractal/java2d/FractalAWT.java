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

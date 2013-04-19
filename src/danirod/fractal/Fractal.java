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
package danirod.fractal;

import java.util.Random;

/**
 * Fractal data structure. Data shown here is output device independent.
 * This means that values stored here may not match actual pixel representation
 * that the viewer represents.
 * 
 * @author danirod
 */
public class Fractal
{
	/**
	 * Actual binary data for the fractal on this iteration.
	 */
	private double[] values;
	
	/**
	 * Defines the absolute value for the range where random numbers
	 * will be generated. Say threshold equals to t, then the range
	 * for those random numbers will be [-t, t].
	 */
	private double threshold;
	
	/**
	 * Initial threshold. In case the generator is reinited this value
	 * will always keep the initial threshold level, as the original
	 * {@link #threshold} field will change during the iterations.
	 */
	private double initialThreshold;
	
	/**
	 * Defines the decay level. Each step on the iteration multiples the
	 * Threshold by this level, which should be inside the range of (0,1).
	 * It should not be neither 0.0 nor 1.0, as these values won't actually
	 * change the value for the threshold after the first step.
	 */
	private double decayLevel;
	
	/**
	 * Default initial threshold level. If an initial threshold is not
	 * provided it's safe to use this as the initial threshold level.
	 */
	private static final double DEFAULT_THRESHOLD = 1.0;
	
	/**
	 * Default decay level. If a decay level is not provided it's safe
	 * to use this as the decay level for each iteration step in the fractal.
	 */
	private static final double DEFAULT_DECAY = 0.5;
	
	/**
	 * PRNG used by the fractal generator. The reason behind using an
	 * actual PRNG instead of just calling {@link Math#random()} is that
	 * {@link Random} class allows to manage seeds, which may be useful
	 * in certain cases.
	 */
	private Random rnd;
	
	/**
	 * Creates a new fractal generator. The fractal uses the default values
	 * for the threshold and for the decay level.
	 */
	public Fractal()
	{
		initialThreshold = DEFAULT_THRESHOLD;
		decayLevel = DEFAULT_DECAY;
	}
	
	/**
	 * Creates a new fractal generator. The values for the threshold and
	 * for the decay level are provided during the construction but they
	 * may be changed at runtime.
	 * 
	 * @param threshold initial threshold level for the generator
	 * @param decay decay factor for decreasing the threshold.
	 */
	public Fractal(double thresold, double decay)
	{
		initialThreshold = thresold;
		decayLevel = decay;
	}
	
	/**
	 * Initialise or reinitialise the fractal. A new fractal is created
	 * having both initial vertex initialised to 0.0. Also, random number
	 * generator seed is reinitialised to a random value.
	 */
	public void init()
	{
		init(System.currentTimeMillis(), 0.0, 0.0);
	}
	
	/**
	 * Initialization process. This method should be called to start or
	 * to restart the generator. When the generator is restarted, a new
	 * fractal with only two values is generated, and the seed is
	 * reinitialized.
	 * 
	 * @param seed seed for the pseudo-random number generator
	 * @param left value for the left edge in the fractal
	 * @param right value for the right edge in the fractal
	 */
	public void init(long seed, double left, double right)
	{
		// Start the system.
		rnd = new Random(seed);
		threshold = initialThreshold;
		
		// Initialise the system with two first values.
		values = new double[2];
		values[0] = left;
		values[1] = right;
	}
	
	/**
	 * Iterate the generator. Each iteration adds a new step and adds some
	 * new points. The process of stepping is described as follows.
	 * 
	 * <p>For each iteration, a new point is added right in the middle of
	 * the gap that is between two points that previously existed in the
	 * fractal. This value is initially set to the average of the value of
	 * the two nearby points, but then that value is increased or decreased
	 * randomly using the threshold level.
	 */
	public void iterate() {
		/*
		 * We create a new set of values. Geometrically one can see that each
		 * iteration has the double of points that the previous iteration minus
		 * one. For instance, for a_0 = 2, it follows as {2,3,5,9,17,...}
		 */
		double[] newval = new double[values.length * 2 - 1];
		newval[0] = values[0]; // left edge always has the same value.
		
		int v, nv = 1;		// array pointers. v = values, nv = new values.
		double mid_y;		// value for the midpoint that gets created
		
		/*
		 * We start the iteration. Because we will work in pairs with one
		 * point and the next point in the original value set, we have to
		 * stop the loop before the last point gets processed.
		 */
		for(v = 0; v < values.length - 1; v++)
		{		
			/*
			 * Each step is pretty much like follows:
			 * 1. Generate a new random point based on points v and v+1.
			 * 2. Copy original v+1 point (it won't change). 
			 */
			
			/*
			 * Generate a new random point based on points v and v+1. First,
			 * we set this new point to have the average value of v and v+1,
			 * so that it looks as if nothing happened. Then, we shift that
			 * value using a random number in the range of
			 * [-threshold,threshold].
			 */
			mid_y = (values[v] + values[v+1]) / 2;
			mid_y += (2.0 * threshold * (rnd.nextDouble() - 0.5));
			newval[nv++] = mid_y;

			// Copy original v+1 point (it won't change).
			newval[nv++] = values[v+1];
		}
		
		values = newval;			// we switch the new data values array
		threshold *= decayLevel;	// and we decay the threshold level.
	}
	
	public double getThreshold()
	{
		return threshold;
	}
	
	public double getInitialThreshold()
	{
		return initialThreshold;
	}
	
	public void setInitialThreshold(double threshold)
	{
		initialThreshold = threshold;
	}
	
	public double getDecayLevel()
	{
		return decayLevel;
	}
	
	public void setDecayLevel(double decay)
	{
		decayLevel = decay;
	}
	
	/**
	 * Return the array set of values.
	 * @return array set of values
	 */
	public double[] getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		String valchar = "";
		for(int i = 0; i < values.length; i++)
			valchar += values[i] + " ";
		valchar = valchar.trim();
		return String.format("T=%f D=%f [%s]", threshold, decayLevel, valchar);
	}
}

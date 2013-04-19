package danirod.fractal.slick2d;

import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import danirod.fractal.Fractal;

/**
 * Fractal basic game for rendering. Includes some advanced code to make
 * it better and to dynamically modify the fractal in real time.
 * 
 * @author danirod
 */
public class FractalGame extends BasicGame {

	private Fractal f;
	
	/**
	 * Number of iterations used by the generator. The more iterations are
	 * done, the smoothest results we will get. However, the more time it
	 * will take.
	 */
	private int itcount;
	
	/**
	 * Random seed used to generate new random seeds.
	 */
	private Random rnd;
	
	/**
	 * Current random seed being used by the fractal generator.
	 */
	private long seed;
	
	public FractalGame(String title) {
		super(title);
		this.f = new Fractal();
		rnd = new Random();
		seed = rnd.nextLong();
		itcount = 8; // initially we set to 8, it's a nice value.
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
	    int width = gc.getWidth(), height = gc.getHeight();
	    
		double[] data = f.getValues(); // value list
		float sep = (float) width / (data.length - 1); // gap between points
		g.setColor(Color.white); // needed in order to see anything
		
		Vector2f pixel0 = new Vector2f(), pixel1 = new Vector2f();
		for(int k = 0; k < data.length - 1; k++) {
		    // generate this point coordinates
			pixel0.x = sep * k;
			pixel0.y = height / 2 - (float) data[k] * height / 2;
			
			// generate next point coordinates
			pixel1.x = sep * (k + 1);
			pixel1.y = height / 2 - (float) data[k+1] * height / 2;
			
			g.drawLine(pixel0.x, pixel0.y, pixel1.x, pixel1.y); // and plot!
		}
		
		// Render some debug information
		String[] debug1 = {
	        "Fractal | FPS: " + gc.getFPS(),
	        "T: " + f.getInitialThreshold() + " | D: " + f.getDecayLevel(),
	        "Seed: " + seed,
	        "Iterations: " + itcount + " | Values: " + data.length,
		};
		String[] debug2 = {
	        "Reset: R - New Seed: S - Change Threshold: UP/DOWN",
	        "Change Decay: LEFT/RIGHT - Change Iterations: A/Q",
		};
		drawStrings(g, debug1, 5, 5);
		drawStrings(g, debug2, 5, height - 2 * g.getFont().getLineHeight() - 5);
		
		// Sea level? Why not
		g.setColor(Color.blue);
		g.drawLine(0, height * 0.6f, width, height * 0.6f);
		g.drawString("Sea Level", 5, height * 0.6f + 5);
	}
	
	/**
	 * Massive string renderer. Each string position in the array is rendered
	 * as a new line. All the lines are rendered together.
	 * @param g graphical context
	 * @param strs string array to render
	 * @param x X position for start rendering
	 * @param y Y position for start rendering
	 */
	private void drawStrings(Graphics g, String[] strs, int x, int y)
	{
	    for(int i = 0; i < strs.length; i++)
	        g.drawString(strs[i], x, y + g.getFont().getLineHeight() * i);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		f.init(seed, 0.0, 0.0);
		for(int i = 0; i < itcount; i++)
			f.iterate();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_R)) { // R: RESET
			f = new Fractal();
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_S)) { // S: RESEED
			seed = rnd.nextLong();
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_UP)) { // UP: T++
			f.setInitialThreshold(f.getInitialThreshold() + 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_DOWN)) { // DOWN: T--
			f.setInitialThreshold(f.getInitialThreshold() - 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_LEFT)) { // LEFT: D--
			f.setDecayLevel(f.getDecayLevel() - 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)) { // RIGHT: D++
			f.setDecayLevel(f.getDecayLevel() + 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_A)) { // A: IT++
			itcount++;
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_Q)) { // Q: IT--
			if(itcount > 0)
				itcount--;
			gc.reinit();
		}
	}

}

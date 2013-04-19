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
	
	private int itcount;
	
	private Random rnd;
	
	private long seed;
	
	public FractalGame(String title) {
		super(title);
		this.f = new Fractal();
		rnd = new Random();
		seed = rnd.nextLong();
		itcount = 8;
	}
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 450;
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		double[] data = f.getValues();
		float sep = (float) WIDTH / (data.length - 1);
		
		g.setColor(Color.white);
		Vector2f pixel0 = new Vector2f(), pixel1 = new Vector2f();
		for(int k = 0; k < data.length - 1; k++) {
			pixel0.x = sep * k;
			pixel0.y = HEIGHT / 2 - (float) data[k] * HEIGHT / 2;
			pixel1.x = sep * (k + 1);
			pixel1.y = HEIGHT / 2 - (float) data[k+1] * HEIGHT / 2;
			g.drawLine(pixel0.x, pixel0.y, pixel1.x, pixel1.y);
		}
		
		g.drawString("Fractal | FPS: " + gc.getFPS(), 5, 5);
		g.drawString("Threshold: " + f.getInitialThreshold() + " | Decay: " + f.getDecayLevel(), 5, 20);
		g.drawString("Seed: " + seed, 5, 35);
		g.drawString("Iterations: " + itcount + " | Values: " + data.length, 5, 50);
		g.drawString("Reset: R - New Seed: S - Change Threshold: UP/DOWN", 5, gc.getHeight() - 35);
		g.drawString("Change Decay: LEFT/RIGHT - Change Iterations: A/Q", 5, gc.getHeight() - 20);
		
		g.setColor(Color.blue);
		g.drawLine(0, HEIGHT * 0.6f, WIDTH, HEIGHT * 0.6f);
		g.drawString("Sea Level", 5, HEIGHT * 0.6f + 5);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		f.init(seed, 0.0, 0.0);
		for(int i = 0; i < itcount; i++)
			f.iterate();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_R)) {
			f = new Fractal();
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_S)) {
			seed = rnd.nextLong();
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_UP)) {
			f.setInitialThreshold(f.getInitialThreshold() + 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			f.setInitialThreshold(f.getInitialThreshold() - 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
			f.setDecayLevel(f.getDecayLevel() - 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
			f.setDecayLevel(f.getDecayLevel() + 0.05);
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_A)) {
			itcount++;
			gc.reinit();
		} else if(gc.getInput().isKeyPressed(Input.KEY_Q)) {
			if(itcount > 0)
				itcount--;
			gc.reinit();
		}
	}

}

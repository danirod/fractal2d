package danirod.fractal.slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import static danirod.fractal.slick2d.FractalGame.HEIGHT;
import static danirod.fractal.slick2d.FractalGame.WIDTH;

public class FractalSlick
{
	public static void main(String args[]) {
		AppGameContainer app = null;
		try {			
			app = new AppGameContainer(new FractalGame("Fractal"));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setShowFPS(false);
			app.start();
		} catch(SlickException slick) {
			slick.printStackTrace();
			if(app != null)
				app.destroy();
		}
	}
}

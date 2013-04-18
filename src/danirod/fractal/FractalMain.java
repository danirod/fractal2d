package danirod.fractal;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import static danirod.fractal.FractalViewer.WIDTH;
import static danirod.fractal.FractalViewer.HEIGHT;

public class FractalMain
{
	public static void main(String args[]) {
		AppGameContainer app = null;
		try {			
			app = new AppGameContainer(new FractalViewer("Fractal"));
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

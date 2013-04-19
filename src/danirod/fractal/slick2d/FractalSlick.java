package danirod.fractal.slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class FractalSlick
{
	public static void main(String args[]) {
		AppGameContainer app = null;
		try {			
			app = new AppGameContainer(new FractalGame("Fractal"));
			app.setDisplayMode(852, 480, false);
			app.setShowFPS(false);
			app.start();
		} catch(SlickException slick) {
			slick.printStackTrace();
			if(app != null)
				app.destroy();
		}
	}
}

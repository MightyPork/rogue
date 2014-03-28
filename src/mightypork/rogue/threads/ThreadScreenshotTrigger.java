package mightypork.rogue.threads;


import mightypork.rogue.App;
import mightypork.rogue.input.Keys;
import mightypork.utils.logging.Log;

import org.lwjgl.input.Keyboard;


/**
 * @author MightyPork
 */
public class ThreadScreenshotTrigger extends Thread {

	@Override
	public void run()
	{
		while (true) {
			if (Keys.justPressed(Keyboard.KEY_F2)) {
				Log.f2("F2, taking screenshot.");
				App.scheduledScreenshot = true;
				Keys.destroyChangeState(Keyboard.KEY_F2);
			}
			try {
				sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

package mightypork.gamecore.core.plugins.screenshot;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.AppPlugin;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.utils.Support;


/**
 * This plugin waits for a {@link ScreenshotRequest} event.<br>
 * Upon receiving it, a screenshot is captured and written to file
 * asynchronously.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ScreenshotPlugin extends AppPlugin {
	
	/**
	 * Take screenshot. Called by the trigger event.
	 */
	void takeScreenshot()
	{
		App.bus().send(new MainLoopRequest(new Runnable() {
			
			@Override
			public void run()
			{
				Runnable tts = new TaskTakeScreenshot();
				Support.runAsThread(tts);
			}
		}));
	}
}

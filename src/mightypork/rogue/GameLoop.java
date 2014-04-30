package mightypork.rogue;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mightypork.gamecore.app.BaseApp;
import mightypork.gamecore.app.MainLoop;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.Screenshot;
import mightypork.gamecore.util.Utils;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


public final class GameLoop extends MainLoop implements ActionRequest.Listener {
	
	public GameLoop(BaseApp app)
	{
		super(app);
	}
	
	
	@Override
	public void requestAction(RequestType request)
	{
		switch (request) {
			case FULLSCREEN:
				queueTask(taskFullscreen);
				break;
			
			case SCREENSHOT:
				queueTask(taskScreenshot);
				break;
			
			case SHUTDOWN:
				queueTask(taskShutdown);
		}
	}
	
	/*
	 * Take a screenshot
	 */
	private final Action taskScreenshot = new Action() {
		
		@Override
		public void execute()
		{
			Res.getEffect("gui.shutter").play(1);
			Utils.runAsThread(new TaskTakeScreenshot());
		}
	};
	
	/*
	 * Shutdown the application
	 */
	private final Action taskShutdown = new Action() {
		
		@Override
		public void execute()
		{
			shutdown();
		}
	};
	
	/*
	 * Toggle fullscreen
	 */
	private final Action taskFullscreen = new Action() {
		
		@Override
		public void execute()
		{
			getDisplay().switchFullscreen();
		}
	};
	
	private class TaskTakeScreenshot implements Runnable {
		
		private final Screenshot scr;
		
		
		public TaskTakeScreenshot()
		{
			scr = getDisplay().takeScreenshot();
		}
		
		
		@Override
		public void run()
		{
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			final String fname = df.format(new Date());
			
			// generate unique filename
			File file;
			int index = 0;
			while (true) {
				file = new File(Paths.SCREENSHOTS, fname + (index > 0 ? "-" + index : "") + ".png");
				if (!file.exists()) break;
				index++;
			}
			
			Log.f3("Saving screenshot to file: " + file);
			
			// save to disk
			try {
				scr.save(file);
			} catch (final IOException e) {
				Log.e("Failed to save screenshot.", e);
			}
		}
		
	}
	
}

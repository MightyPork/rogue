package mightypork.rogue;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.DisplaySystem.Screenshot;
import mightypork.utils.logging.Log;


public class TaskTakeScreenshot implements Runnable {
	
	private final Screenshot scr;
	
	
	public TaskTakeScreenshot(DisplaySystem disp) {
		scr = disp.takeScreenshot();
	}
	
	
	@Override
	public void run()
	{
		final String fname = getUniqueScreenshotName();
		
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
	
	
	private static String getUniqueScreenshotName()
	{
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return df.format(new Date());
	}
	
}

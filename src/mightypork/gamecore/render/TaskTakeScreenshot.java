package mightypork.gamecore.render;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.Utils;

import org.newdawn.slick.opengl.GLUtils;


public class TaskTakeScreenshot implements Runnable {
	
	private final Screenshot scr;
	
	
	public TaskTakeScreenshot()
	{
		GLUtils.checkGLContext();
		scr = DisplaySystem.prepareScreenshot();
	}
	
	
	@Override
	public void run()
	{
		final String fname = Utils.getTime("yyyy-MM-dd_HH-mm-ss");
		
		// generate unique filename
		File file;
		int index = 0;
		while (true) {
			file = new File(WorkDir.getDir("_screenshot_dir"), fname + (index > 0 ? "-" + index : "") + ".png");
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

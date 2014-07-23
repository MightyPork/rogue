package mightypork.gamecore.render;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.core.modules.App;
import mightypork.utils.Support;
import mightypork.utils.logging.Log;

import org.newdawn.slick.opengl.GLUtils;


/**
 * Task that takes screenshot and asynchronously saves it to a file.<br>
 * Can be run in a separate thread, but must be instantiated in the render
 * thread.
 * 
 * @author MightyPork
 */
public class TaskTakeScreenshot implements Runnable {
	
	private final Screenshot scr;
	
	
	public TaskTakeScreenshot() {
		GLUtils.checkGLContext();
		scr = App.gfx().takeScreenshot();
	}
	
	
	@Override
	public void run()
	{
		final String fname = Support.getTime("yyyy-MM-dd_HH-mm-ss");
		
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

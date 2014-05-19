package mightypork.gamecore.render;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mightypork.gamecore.WorkDir;
import mightypork.gamecore.logging.Log;

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
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		final String fname = df.format(new Date());
		
		// generate unique filename
		File file;
		int index = 0;
		while (true) {
			file = new File(WorkDir.getDir("screenshots"), fname + (index > 0 ? "-" + index : "") + ".png");
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

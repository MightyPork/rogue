package mightypork.gamecore.core.plugins.screenshot;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.core.App;
import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.graphics.Screenshot;
import mightypork.utils.Support;
import mightypork.utils.logging.Log;


/**
 * Task that takes screenshot and asynchronously saves it to a file.<br>
 * Can be run in a separate thread, but must be instantiated in the render
 * thread.
 * 
 * @author MightyPork
 */
public class TaskTakeScreenshot implements Runnable {
	
	private final Screenshot scr;
	
	
	/**
	 * Take screenshot. Must be called in render thread.
	 */
	public TaskTakeScreenshot() {
		scr = App.gfx().takeScreenshot();
	}
	
	
	@Override
	public void run()
	{
		// generate unique filename
		final File file = getScreenshotFile();
		
		Log.f3("Saving screenshot to file: " + file);
		
		// save to disk
		try {
			scr.save(file);
		} catch (final IOException e) {
			Log.e("Failed to save screenshot.", e);
		}
	}
	
	
	/**
	 * @return File to save the screenshot to.
	 */
	protected File getScreenshotFile()
	{
		final String fname = getBaseFilename();
		return findFreeFile(fname);
	}
	
	
	/**
	 * @return directory for screenshots
	 */
	protected File getScreenshotDirectory()
	{
		return WorkDir.getDir("_screenshot_dir");
	}
	
	
	/**
	 * Get base filename for the screenshot, without extension.
	 * 
	 * @return filename
	 */
	protected String getBaseFilename()
	{
		return Support.getTime("yyyy-MM-dd_HH-mm-ss");
	}
	
	
	/**
	 * Find first free filename for the screenshot, by adding -NUMBER after the
	 * base filename and before extension.
	 * 
	 * @param base_name base filename
	 * @return full path to screenshot file
	 */
	protected File findFreeFile(String base_name)
	{
		File file;
		int index = 0;
		while (true) {
			file = new File(getScreenshotDirectory(), base_name + (index > 0 ? "-" + index : "") + ".png");
			if (!file.exists()) break;
			index++;
		}
		return file;
	}
	
}

package mightypork.rogue.tasks;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import mightypork.rogue.Paths;
import mightypork.rogue.display.DisplaySystem;
import mightypork.utils.logging.Log;


public class TaskTakeScreenshot implements Runnable {

	private BufferedImage image;


	public TaskTakeScreenshot(DisplaySystem disp) {
		this.image = disp.takeScreenshot();
	}


	@Override
	public void run()
	{
		String fname = getUniqueScreenshotName();

		// generate unique filename
		File file;
		int index = 0;
		while (true) {
			file = new File(Paths.SCREENSHOTS, fname + (index > 0 ? "-" + index : "") + ".png");
			if (!file.exists()) break;
			index++;
		}

		Log.f3("Saving screenshot to file: " + file);

		String format = "PNG";

		// save to disk
		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			Log.e("Failed to save screenshot.", e);
		}
	}


	private String getUniqueScreenshotName()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return df.format(new Date());
	}

}

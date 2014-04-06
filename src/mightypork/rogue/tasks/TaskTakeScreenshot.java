package mightypork.rogue.tasks;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import mightypork.rogue.Paths;
import mightypork.rogue.render.DisplaySystem;
import mightypork.rogue.render.DisplaySystem.Screenshot;
import mightypork.utils.logging.Log;


public class TaskTakeScreenshot implements Runnable {
	
	private final Screenshot scr;
	
	
	public TaskTakeScreenshot(DisplaySystem disp) {
		scr = disp.takeScreenshot();
	}
	
	
	@Override
	public void run()
	{
		
		final BufferedImage image = new BufferedImage(scr.width, scr.height, BufferedImage.TYPE_INT_RGB);
		
		// convert to a buffered image
		for (int x = 0; x < scr.width; x++) {
			for (int y = 0; y < scr.height; y++) {
				final int i = (x + (scr.width * y)) * scr.bpp;
				final int r = scr.bytes.get(i) & 0xFF;
				final int g = scr.bytes.get(i + 1) & 0xFF;
				final int b = scr.bytes.get(i + 2) & 0xFF;
				image.setRGB(x, scr.height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		
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
		
		final String format = "PNG";
		
		// save to disk
		try {
			ImageIO.write(image, format, file);
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

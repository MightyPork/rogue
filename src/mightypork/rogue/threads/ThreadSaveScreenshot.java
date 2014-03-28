package mightypork.rogue.threads;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import mightypork.rogue.Paths;
import mightypork.utils.logging.Log;


/**
 * Thread for saving screenshot
 * 
 * @author MightyPork
 */
public class ThreadSaveScreenshot extends Thread {

	private ByteBuffer buffer;
	private int width;
	private int height;
	private int bpp;


	/**
	 * Save screenshot thread
	 * 
	 * @param buffer byte buffer with image data
	 * @param width screen width
	 * @param height screen height
	 * @param bpp bits per pixel
	 */
	public ThreadSaveScreenshot(ByteBuffer buffer, int width, int height, int bpp) {
		this.buffer = buffer;
		this.width = width;
		this.height = height;
		this.bpp = bpp;
	}


	private String getUniqueScreenshotName()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return df.format(new Date());
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
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// convert to a buffered image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		// save to disk
		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			Log.e("Failed to save screenshot.", e);
		}
	}
}

package mightypork.gamecore.backends.lwjgl.graphics;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import mightypork.gamecore.graphics.Screenshot;


/**
 * Screenshot object, can be used to extract image or write to file.<br>
 * Screenshot, once taken, can be safely processed in separate thread.<br>
 * Based on {@link BufferedImage} and {@link ImageIO}.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class AwtScreenshot implements Screenshot {
	
	private final int width;
	private final int height;
	private final int bpp;
	private final ByteBuffer bytes;
	private BufferedImage image;
	
	
	/**
	 * @param width image width
	 * @param height image height
	 * @param bpp bits per pixel (typically 4)
	 * @param buffer
	 */
	public AwtScreenshot(int width, int height, int bpp, ByteBuffer buffer) {
		this.width = width;
		this.height = height;
		this.bpp = bpp;
		this.bytes = buffer;
	}
	
	
	/**
	 * Extract to an image.<br>
	 * Subsequent calls will use a cached value.
	 * 
	 * @return image
	 */
	public BufferedImage getImage()
	{
		if (image != null) return image;
		
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		
		// convert to a buffered image
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				final int i = (x + (this.width * y)) * this.bpp;
				final int r = this.bytes.get(i) & 0xFF;
				final int g = this.bytes.get(i + 1) & 0xFF;
				final int b = this.bytes.get(i + 2) & 0xFF;
				image.setRGB(x, this.height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		
		return image;
	}
	
	
	/**
	 * Save to a file.<br>
	 * Cached value is used if any.
	 * 
	 * @param file target file
	 * @throws IOException on error writing to file
	 */
	@Override
	public void save(File file) throws IOException
	{
		file.getParentFile().mkdirs();
		ImageIO.write(getImage(), "PNG", file);
	}
}

package mightypork.rogue.fonts;

import static mightypork.rogue.fonts.Align.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mightypork.rogue.Config;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


/**
 * A TrueType font implementation originally for Slick, edited for Bobjob's
 * Engine
 * 
 * @original author James Chambers (Jimmy)
 * @original author Jeremy Adams (elias4444)
 * @original author Kevin Glass (kevglass)
 * @original author Peter Korzuszek (genail)
 * @new version edited by David Aaron Muhar (bobjob)
 * @new version edited by MightyPork
 */
public class LoadedFont {
	
	private static final boolean DEBUG = Config.LOG_FONTS;
	
	/** Map of user defined font characters (Character <-> IntObject) */
	private Map<Character, CharStorageEntry> chars = new HashMap<Character, CharStorageEntry>(100);
	
	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias;
	
	/** Font's size */
	private int fontSize = 0;
	
	/** Font's height */
	private int fontHeight = 0;
	
	/** Texture used to cache the font 0-255 characters */
	private int fontTextureID;
	
	/** Default font texture width */
	private int textureWidth = 2048;
	
	/** Default font texture height */
	private int textureHeight = 2048;
	
	/** A reference to Java's AWT Font that we create our font texture from */
	private Font font;
	
	/** The font metrics for our Java AWT font */
	private FontMetrics fontMetrics;
	
	private int correctL = 9, correctR = 8;
	
	private double defScaleX = 1, defScaleY = 1;
	private double clipVerticalT = 0;
	private double clipVerticalB = 0;
	
	
	private class CharStorageEntry {
		
		/** Character's width */
		public int width;
		
		/** Character's height */
		public int height;
		
		/** Character's stored x position */
		public int texPosX;
		
		/** Character's stored y position */
		public int texPosY;
	}
	
	
	public LoadedFont(Font font, boolean antiAlias, String charsNeeded) {
		this.font = font;
		this.fontSize = font.getSize() + 3;
		this.antiAlias = antiAlias;
		
		createSet(charsNeeded.toCharArray());
		
		fontHeight -= 1;
		if (fontHeight <= 0) fontHeight = 1;
	}
	
	
	public void setCorrection(boolean on)
	{
		if (on) {
			correctL = 9;// 2
			correctR = 8;// 1
		} else {
			correctL = 0;
			correctR = 0;
		}
	}
	
	
	private BufferedImage getFontImage(char ch)
	{
		// Create a temporary image to extract the character's size
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if (antiAlias == true) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch) + 8;
		
		if (charwidth <= 0) {
			charwidth = 7;
		}
		int charheight = fontMetrics.getHeight() + 3;
		if (charheight <= 0) {
			charheight = fontSize;
		}
		
		// Create another image holding the character we are creating
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if (antiAlias == true) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		gt.setFont(font);
		
		gt.setColor(Color.WHITE);
		int charx = 3;
		int chary = 1;
		gt.drawString(String.valueOf(ch), (charx), (chary) + fontMetrics.getAscent());
		
		return fontImage;
		
	}
	
	
	private void createSet(char[] charsToLoad)
	{
		try {
			class LoadedGlyph {
				
				public char c;
				public BufferedImage image;
				public int width;
				public int height;
				
				
				public LoadedGlyph(char c, BufferedImage image) {
					this.image = image;
					this.c = c;
					this.width = image.getWidth();
					this.height = image.getHeight();
				}
			}
			
			List<LoadedGlyph> glyphs = new ArrayList<LoadedGlyph>();
			List<Character> loaded = new ArrayList<Character>();
			for (char ch : charsToLoad) {
				if (!loaded.contains(ch)) {
					glyphs.add(new LoadedGlyph(ch, getFontImage(ch)));
					loaded.add(ch);
				}
			}
			
			Coord canvas = new Coord(128, 128);
			double lineHeight = 0;
			Coord begin = new Coord(0, 0);
			boolean needsLarger = false;
			
			while (true) {
				needsLarger = false;
				
				for (LoadedGlyph glyph : glyphs) {
					if (begin.x + glyph.width > canvas.x) {
						begin.y += lineHeight;
						lineHeight = 0;
						begin.x = 0;
					}
					
					if (lineHeight < glyph.height) {
						lineHeight = glyph.height;
					}
					
					if (begin.y + lineHeight > canvas.y) {
						needsLarger = true;
						break;
					}
					
					// draw.
					begin.x += glyph.width;
				}
				
				if (needsLarger) {
					canvas.x *= 2;
					canvas.y *= 2;
					begin.setTo(0, 0);
					lineHeight = 0;
				} else {
					if (DEBUG) Log.f3("Preparing texture " + canvas.x + "x" + canvas.y);
					break;
				}
			}
			
			textureWidth = (int) canvas.x;
			textureHeight = (int) canvas.y;
			
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();
			
			g.setColor(new Color(0, 0, 0, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);
			
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			
			for (LoadedGlyph glyph : glyphs) {
				CharStorageEntry storedChar = new CharStorageEntry();
				
				storedChar.width = glyph.width;
				storedChar.height = glyph.height;
				
				if (positionX + storedChar.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}
				
				storedChar.texPosX = positionX;
				storedChar.texPosY = positionY;
				
				if (storedChar.height > fontHeight) {
					fontHeight = storedChar.height;
				}
				
				if (storedChar.height > rowHeight) {
					rowHeight = storedChar.height;
				}
				
				// Draw it here
				g.drawImage(glyph.image, positionX, positionY, null);
				
				positionX += storedChar.width;
				
				chars.put(glyph.c, storedChar);
			}
			
			fontTextureID = loadImage(imgTemp);
			
			imgTemp = null;
			
		} catch (Exception e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	
	private void drawQuad(double drawX, double drawY, double drawX2, double drawY2, CharStorageEntry charObj)
	{
		double srcX = charObj.texPosX + charObj.width;
		double srcY = charObj.texPosY + charObj.height;
		double srcX2 = charObj.texPosX;
		double srcY2 = charObj.texPosY;
		double DrawWidth = drawX2 - drawX;
		double DrawHeight = drawY2 - drawY;
		double TextureSrcX = srcX / textureWidth;
		double TextureSrcY = srcY / textureHeight;
		double SrcWidth = srcX2 - srcX;
		double SrcHeight = srcY2 - srcY;
		double RenderWidth = (SrcWidth / textureWidth);
		double RenderHeight = (SrcHeight / textureHeight);
		
		drawY -= DrawHeight * clipVerticalB;
		
		GL11.glTexCoord2d(TextureSrcX, TextureSrcY);
		GL11.glVertex2d(drawX, drawY);
		GL11.glTexCoord2d(TextureSrcX, TextureSrcY + RenderHeight);
		GL11.glVertex2d(drawX, drawY + DrawHeight);
		GL11.glTexCoord2d(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
		GL11.glVertex2d(drawX + DrawWidth, drawY + DrawHeight);
		GL11.glTexCoord2d(TextureSrcX + RenderWidth, TextureSrcY);
		GL11.glVertex2d(drawX + DrawWidth, drawY);
	}
	
	
	public int getWidth(String whatchars)
	{
		if (whatchars == null) whatchars = "";
		int totalwidth = 0;
		CharStorageEntry charStorage = null;
		char currentChar = 0;
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			
			charStorage = chars.get(currentChar);
			
			if (charStorage != null) {
				totalwidth += charStorage.width - correctL;
			}
		}
		return (int) (totalwidth * defScaleX);
	}
	
	
	public int getHeight()
	{
		return (int) (fontHeight * defScaleY * (1 - clipVerticalT - clipVerticalB));
	}
	
	
	public int getLineHeight()
	{
		return getHeight();
	}
	
	
	public void drawString(double x, double y, String text, double scaleX, double scaleY, RGB color)
	{
		drawString(x, y, text, 0, text.length() - 1, scaleX, scaleY, color, LEFT);
	}
	
	
	public void drawString(double x, double y, String text, double scaleX, double scaleY, RGB color, int align)
	{
		drawString(x, y, text, 0, text.length() - 1, scaleX, scaleY, color, align);
	}
	
	
	private void drawString(double x, double y, String text, int startIndex, int endIndex, double scaleX, double scaleY, RGB color, int align)
	{
		x = Math.round(x);
		y = Math.round(y);
		
		scaleX *= defScaleX;
		scaleY *= defScaleY;
		
		CharStorageEntry charStorage = null;
		int charCurrent;
		
		int totalwidth = 0;
		int i = startIndex, d = 1, c = correctL;
		float startY = 0;
		
		switch (align) {
			case RIGHT: {
				d = -1;
				c = correctR;
				
				while (i < endIndex) {
					if (text.charAt(i) == '\n') startY -= getHeight();
					i++;
				}
				break;
			}
			case CENTER: {
				for (int l = startIndex; l <= endIndex; l++) {
					charCurrent = text.charAt(l);
					if (charCurrent == '\n') break;
					
					charStorage = chars.get((char) charCurrent);
					if (charStorage != null) {
						totalwidth += charStorage.width - correctL;
					}
				}
				totalwidth /= -2;
				break;
			}
			case LEFT:
			default: {
				d = 1;
				c = correctL;
				break;
			}
			
		}
		
		GL11.glPushAttrib(GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureID);
		GL11.glColor4d(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_QUADS);
		
		while (i >= startIndex && i <= endIndex) {
			charCurrent = text.charAt(i);
			
			charStorage = chars.get(new Character((char) charCurrent));
			
			if (charStorage != null) {
				if (d < 0) totalwidth += (charStorage.width - c) * d;
				if (charCurrent == '\n') {
					startY -= getHeight() * d;
					totalwidth = 0;
					if (align == CENTER) {
						for (int l = i + 1; l <= endIndex; l++) {
							charCurrent = text.charAt(l);
							if (charCurrent == '\n') break;
							
							charStorage = chars.get((char) charCurrent);
							
							totalwidth += charStorage.width - correctL;
						}
						totalwidth /= -2;
					}
					// if center get next lines total width/2;
				} else {
					//@formatter:off
					drawQuad(
								(totalwidth + charStorage.width) * scaleX + x,
								startY * scaleY + y, totalwidth * scaleX + x,
								(startY + charStorage.height)	* scaleY + y,
								charStorage
							);
					//@formatter:on
					
					if (d > 0) totalwidth += (charStorage.width - c) * d;
				}
				
			}
			
			i += d;
		}
		GL11.glEnd();
		GL11.glPopAttrib();
	}
	
	
	public static int loadImage(BufferedImage bufferedImage)
	{
		try {
			short width = (short) bufferedImage.getWidth();
			short height = (short) bufferedImage.getHeight();
			// textureLoader.bpp = bufferedImage.getColorModel().hasAlpha() ?
			// (byte)32 : (byte)24;
			int bpp = (byte) bufferedImage.getColorModel().getPixelSize();
			ByteBuffer byteBuffer;
			DataBuffer db = bufferedImage.getData().getDataBuffer();
			if (db instanceof DataBufferInt) {
				int intI[] = ((DataBufferInt) (bufferedImage.getData().getDataBuffer())).getData();
				byte newI[] = new byte[intI.length * 4];
				for (int i = 0; i < intI.length; i++) {
					byte b[] = intToByteArray(intI[i]);
					int newIndex = i * 4;
					
					newI[newIndex] = b[1];
					newI[newIndex + 1] = b[2];
					newI[newIndex + 2] = b[3];
					newI[newIndex + 3] = b[0];
				}
				
				byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder()).put(newI);
			} else {
				byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder()).put(((DataBufferByte) (bufferedImage.getData().getDataBuffer())).getData());
			}
			byteBuffer.flip();
			
			int internalFormat = GL11.GL_RGBA8, format = GL11.GL_RGBA;
			IntBuffer textureId = BufferUtils.createIntBuffer(1);
			
			GL11.glGenTextures(textureId);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId.get(0));
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			
			GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			
			GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, internalFormat, width, height, format, GL11.GL_UNSIGNED_BYTE, byteBuffer);
			return textureId.get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return -1;
	}
	
	
	public static boolean isSupported(String fontname)
	{
		Font font[] = getFonts();
		for (int i = font.length - 1; i >= 0; i--) {
			if (font[i].getName().equalsIgnoreCase(fontname)) return true;
		}
		return false;
	}
	
	
	public static Font[] getFonts()
	{
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	}
	
	
	public static Font getFont(String fontname, int style, float size)
	{
		Font result = null;
		GraphicsEnvironment graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (Font font : graphicsenvironment.getAllFonts()) {
			if (font.getName().equalsIgnoreCase(fontname)) {
				result = font.deriveFont(style, size);
				break;
			}
		}
		return result;
	}
	
	
	public static byte[] intToByteArray(int value)
	{
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}
	
	
	public void destroy()
	{
		IntBuffer scratch = BufferUtils.createIntBuffer(1);
		scratch.put(0, fontTextureID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDeleteTextures(scratch);
	}
	
	
	public LoadedFont setScale(double x, double y)
	{
		defScaleX = x;
		defScaleY = y;
		return this;
	}
	
	
	public LoadedFont setClip(double clipRatioTop, double clipRatioBottom)
	{
		clipVerticalT = clipRatioTop;
		clipVerticalB = clipRatioBottom;
		return this;
	}
	
	
	public LoadedFont setCorrection(int correctionLeft, int correctionRight)
	{
		correctL = correctionLeft;
		correctR = correctionRight;
		return this;
	}
	
	
	/**
	 * Draw string with font.
	 * 
	 * @param x
	 *            x coord
	 * @param y
	 *            y coord
	 * @param text
	 *            text to draw
	 * @param color
	 *            render color
	 * @param align
	 *            (-1,0,1)
	 */
	public void draw(double x, double y, String text, RGB color, int align)
	{
		drawString(x, y, text, 1, 1, color, align);
	}
	
	
	/**
	 * Draw string with font.
	 * 
	 * @param pos
	 *            coord
	 * @param text
	 *            text to draw
	 * @param color
	 *            render color
	 * @param align
	 *            (-1,0,1)
	 */
	public void draw(Coord pos, String text, RGB color, int align)
	{
		drawString(pos.x, pos.y, text, 1, 1, color, align);
	}
	
	
	public void drawFuzzy(Coord pos, String text, int align, RGB textColor, RGB blurColor, int blurSize)
	{
		drawFuzzy(pos, text, align, textColor, blurColor, blurSize, true);
	}
	
	
	public void drawFuzzy(Coord pos, String text, int align, RGB textColor, RGB blurColor, int blurSize, boolean smooth)
	{
		glPushMatrix();
		
		glTranslated(pos.x, pos.y, pos.z);
		
		// shadow
		int sh = blurSize;
		
		int l = glGenLists(1);
		
		glNewList(l, GL_COMPILE);
		draw(0, 0, text, blurColor, align);
		glEndList();
		
		for (int xx = -sh; xx <= sh; xx += (smooth ? 1 : sh)) {
			for (int yy = -sh; yy <= sh; yy += (smooth ? 1 : sh)) {
				if (xx == 0 && yy == 0) continue;
				glPushMatrix();
				glTranslated(xx, yy, 0);
				glCallList(l);
				glPopMatrix();
			}
		}
		
		glDeleteLists(l, 1);
		
		draw(0, 0, text, textColor, align);
		
		glPopMatrix();
	}
	
}

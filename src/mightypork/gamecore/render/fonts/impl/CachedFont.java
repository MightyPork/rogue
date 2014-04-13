package mightypork.gamecore.render.fonts.impl;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.textures.FilterMode;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.vect.VectVal;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.GLUtils;


/**
 * A TrueType font renderer with backing texture.
 * 
 * @author James Chambers (Jimmy)
 * @author Jeremy Adams (elias4444)
 * @author Kevin Glass (kevglass)
 * @author Peter Korzuszek (genail)
 * @author David Aaron Muhar (bobjob)
 * @author MightyPork
 */
public class CachedFont implements GLFont {
	
	private class CharTile {
		
		public int width;
		public int height;
		public int texPosX;
		public int texPosY;
	}
	
	/* char bank */
	private final Map<Character, CharTile> chars = new HashMap<>(255);
	
	/* use antialiasing for rendering */
	private final boolean antiAlias;
	
	/* loaded font size (requested) */
	private final int fontSize;
	
	/* actual height of drawn glyphs */
	private int fontHeight;
	
	/* texture id */
	private int textureID;
	
	/* texture width */
	private int textureWidth;
	
	/* texture height */
	private int textureHeight;
	
	/* AWT font source */
	private final java.awt.Font font;
	
	private final FilterMode filter;
	
	
	/**
	 * Make a font
	 * 
	 * @param font original awt font to load
	 * @param antialias use antialiasing when rendering to cache texture
	 * @param filter used Gl filter
	 * @param chars chars to load
	 */
	public CachedFont(java.awt.Font font, boolean antialias, FilterMode filter, String chars) {
		this(font, antialias, filter, chars.toCharArray());
	}
	
	
	/**
	 * Make a font
	 * 
	 * @param font original awt font to load
	 * @param antialias use antialiasing when rendering to cache texture
	 * @param filter used Gl filter
	 * @param chars chars to load
	 */
	public CachedFont(java.awt.Font font, boolean antialias, FilterMode filter, char[] chars) {
		GLUtils.checkGLContext();
		
		this.font = font;
		this.filter = filter;
		this.fontSize = font.getSize();
		this.antiAlias = antialias;
		
		createSet(chars);
	}
	
	
	/**
	 * Create a BufferedImage of the given character
	 * 
	 * @param ch the character
	 * @return BufferedImage containing the drawn character
	 */
	private BufferedImage getFontImage(char ch)
	{
		FontMetrics metrics;
		BufferedImage img;
		Graphics2D g;
		
		// Create a temporary image to extract the character's size
		img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		g = (Graphics2D) img.getGraphics();
		if (antiAlias == true) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		
		metrics = g.getFontMetrics();
		
		final int charwidth = Math.max(1, metrics.charWidth(ch));
		final int charheight = Math.max(fontSize, metrics.getHeight());
		
		// Create another image holding the character we are creating
		final BufferedImage fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		
		g = (Graphics2D) fontImage.getGraphics();
		if (antiAlias == true) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(ch), 0, metrics.getAscent());
		
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
			
			final List<LoadedGlyph> glyphs = new ArrayList<>();
			final List<Character> loaded = new ArrayList<>();
			for (final char ch : charsToLoad) {
				if (!loaded.contains(ch)) {
					glyphs.add(new LoadedGlyph(ch, getFontImage(ch)));
					loaded.add(ch);
				}
			}
			
			int lineHeight = 0;
			
			int beginX = 0, beginY = 0;
			int canvasW = 128, canvasH = 128;
			
			boolean needsLarger = false;
			
			// find smallest 2^x size for texture
			while (true) {
				needsLarger = false;
				
				for (final LoadedGlyph glyph : glyphs) {
					if (beginX + glyph.width > canvasW) {
						beginY += lineHeight;
						lineHeight = 0;
						beginX = 0;
					}
					
					if (lineHeight < glyph.height) {
						lineHeight = glyph.height;
					}
					
					if (beginY + lineHeight > canvasH) {
						needsLarger = true;
						break;
					}
					
					// draw.
					beginX += glyph.width;
				}
				
				if (needsLarger) {
					canvasW *= 2;
					canvasH *= 2;
					beginX = 0;
					beginY = 0;
					lineHeight = 0;
				} else {
					Log.f3(String.format("Generating font texture: %d×%d", canvasW, canvasH));
					break;
				}
			}
			
			textureWidth = canvasW;
			textureHeight = canvasH;
			
			BufferedImage imag = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g = (Graphics2D) imag.getGraphics();
			
			g.setColor(new Color(0, 0, 0, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);
			
			int rowHeight = 0, posX = 0, posY = 0;
			
			for (final LoadedGlyph glyph : glyphs) {
				final CharTile cht = new CharTile();
				
				cht.width = glyph.width;
				cht.height = glyph.height;
				
				if (posX + cht.width >= textureWidth) {
					posX = 0;
					posY += rowHeight;
					rowHeight = 0;
				}
				
				cht.texPosX = posX;
				cht.texPosY = posY;
				
				if (cht.height > fontHeight) {
					fontHeight = cht.height;
				}
				
				if (cht.height > rowHeight) {
					rowHeight = cht.height;
				}
				
				// Draw it here
				g.drawImage(glyph.image, posX, posY, null);
				
				posX += cht.width;
				
				chars.put(glyph.c, cht);
			}
			
			textureID = loadImage(imag);
			
			imag = null;
			
		} catch (final Exception e) {
			Log.e("Failed to load font.", e);
		}
	}
	
	
	private int loadImage(BufferedImage bufferedImage)
	{
		try {
			final short width = (short) bufferedImage.getWidth();
			final short height = (short) bufferedImage.getHeight();
			final int bpp = (byte) bufferedImage.getColorModel().getPixelSize();
			
			ByteBuffer byteBuffer;
			final DataBuffer db = bufferedImage.getData().getDataBuffer();
			if (db instanceof DataBufferInt) {
				final int intI[] = ((DataBufferInt) (bufferedImage.getData().getDataBuffer())).getData();
				final byte newI[] = new byte[intI.length * 4];
				for (int i = 0; i < intI.length; i++) {
					final byte b[] = intToByteArray(intI[i]);
					final int newIndex = i * 4;
					
					newI[newIndex] = b[1];
					newI[newIndex + 1] = b[2];
					newI[newIndex + 2] = b[3];
					newI[newIndex + 3] = b[0];
				}
				
				byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder()).put(newI);
			} else {
				byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder())
						.put(((DataBufferByte) (bufferedImage.getData().getDataBuffer())).getData());
			}
			
			byteBuffer.flip();
			
			final int internalFormat = GL_RGBA8, format = GL_RGBA;
			final IntBuffer textureId = BufferUtils.createIntBuffer(1);
			
			glGenTextures(textureId);
			glBindTexture(GL_TEXTURE_2D, textureId.get(0));
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.num);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
			
			GLU.gluBuild2DMipmaps(GL_TEXTURE_2D, internalFormat, width, height, format, GL_UNSIGNED_BYTE, byteBuffer);
			return textureId.get(0);
			
		} catch (final Exception e) {
			Log.e("Failed to load font.", e);
		}
		
		return -1;
	}
	
	
	private static byte[] intToByteArray(int value)
	{
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}
	
	
	private void drawQuad(float xmin, float ymin, float xmax, float ymax, float txmin, float tymin, float txmax, float tymax)
	{
		final float draw_h = xmax - xmin;
		final float draw_w = ymax - ymin;
		final float txmin01 = txmin / textureWidth;
		final float tymin01 = tymin / textureHeight;
		final float twidth01 = ((txmax - txmin) / textureWidth);
		final float theight01 = ((tymax - tymin) / textureHeight);
		
		glTexCoord2f(txmin01, tymin01);
		glVertex2f(xmin, ymin);
		
		glTexCoord2f(txmin01, tymin01 + theight01);
		glVertex2f(xmin, ymin + draw_w);
		
		glTexCoord2f(txmin01 + twidth01, tymin01 + theight01);
		glVertex2f(xmin + draw_h, ymin + draw_w);
		
		glTexCoord2f(txmin01 + twidth01, tymin01);
		glVertex2f(xmin + draw_h, ymin);
	}
	
	
	/**
	 * Get size needed to draw given string
	 * 
	 * @param text drawn text
	 * @return needed width
	 */
	@Override
	public int getWidth(String text)
	{
		int totalwidth = 0;
		CharTile ch = null;
		for (int i = 0; i < text.length(); i++) {
			
			ch = chars.get(text.charAt(i));
			
			if (ch != null) totalwidth += ch.width;
		}
		return totalwidth;
	}
	
	
	@Override
	public int getLineHeight()
	{
		return fontHeight;
	}
	
	
	@Override
	public int getFontSize()
	{
		return fontSize;
	}
	
	
	@Override
	public void draw(String text, RGB color)
	{
		GLUtils.checkGLContext();
		
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureID);
		glColor4d(color.r, color.g, color.b, color.a);
		glBegin(GL_QUADS);
		
		CharTile chtx = null;
		char charCurrent;
		
		glBegin(GL_QUADS);
		
		int totalwidth = 0;
		for (int i = 0; i < text.length(); i++) {
			charCurrent = text.charAt(i);
			
			chtx = chars.get(charCurrent);
			
			if (chtx != null) {
				drawQuad((totalwidth), 0, (totalwidth + chtx.width), (chtx.height), chtx.texPosX, chtx.texPosY, chtx.texPosX + chtx.width, chtx.texPosY
						+ chtx.height);
				totalwidth += chtx.width;
			}
		}
		
		glEnd();
		glPopAttrib();
	}
	
	
	@Override
	public VectVal getNeededSpace(String text)
	{
		return VectVal.make(getWidth(text), getLineHeight());
	}
	
}

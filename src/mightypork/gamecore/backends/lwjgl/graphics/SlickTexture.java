package mightypork.gamecore.backends.lwjgl.graphics;


import java.io.IOException;

import mightypork.gamecore.graphics.textures.DeferredTexture;
import mightypork.gamecore.resources.loading.MustLoadInRenderingContext;
import mightypork.utils.annotations.Alias;
import mightypork.utils.exceptions.IllegalValueException;
import mightypork.utils.files.FileUtil;
import mightypork.utils.logging.Log;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


/**
 * Deferred texture
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Texture")
@MustLoadInRenderingContext
public class SlickTexture extends DeferredTexture {
	
	private org.newdawn.slick.opengl.Texture backingTexture;
	private boolean alpha;
	private boolean alphal;
	
	
	/**
	 * @param resourcePath resource path
	 */
	public SlickTexture(String resourcePath) {
		super(resourcePath);
	}
	
	
	@Override
	protected synchronized void loadResource(String path)
	{
		try {
			final String ext = FileUtil.getExtension(path).toUpperCase();
			
			final int filtering;
			switch (filter) {
				case NEAREST:
					filtering = GL11.GL_NEAREST;
					break;
				case LINEAR:
					filtering = GL11.GL_LINEAR;
					break;
				default:
					throw new IllegalValueException("Unsupported filtering mode.");
			}
			
			final Texture texture = TextureLoader.getTexture(ext, FileUtil.getResource(path), false, filtering);
			
			if (texture == null) {
				Log.w("Texture " + path + " could not be loaded.");
			}
			
			backingTexture = texture;
			
		} catch (final IOException e) {
			Log.e("Loading of texture " + path + " failed.", e);
			throw new RuntimeException("Could not load texture " + path + ".", e);
		}
	}
	
	
	@Override
	public boolean hasAlpha()
	{
		if (!ensureLoaded()) return false;
		
		if (!alphal) {
			alphal = true;
			alpha = backingTexture.hasAlpha();
		}
		
		return alpha;
	}
	
	
	/**
	 * Bind to GL context, applying the filters prescribed.
	 */
	public void bind()
	{
		if (!ensureLoaded()) return;
		
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureID());
		
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		
		final int wrapping;
		switch (wrap) {
			case CLAMP:
				wrapping = GL11.GL_CLAMP;
				break;
			case REPEAT:
				wrapping = GL11.GL_REPEAT;
				break;
			default:
				throw new IllegalValueException("Unsupported wrapping mode.");
		}
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapping);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapping);
		
		final int filtering;
		switch (filter) {
			case NEAREST:
				filtering = GL11.GL_NEAREST;
				break;
			case LINEAR:
				filtering = GL11.GL_LINEAR;
				break;
			default:
				throw new IllegalValueException("Unsupported filtering mode.");
		}
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filtering);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filtering);
	}
	
	
	@Override
	public int getImageHeight()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getImageHeight();
	}
	
	
	@Override
	public int getImageWidth()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getImageWidth();
	}
	
	
	@Override
	public void destroy()
	{
		if (!isLoaded()) return;
		
		backingTexture.release();
	}
	
	
	/**
	 * Get the height of the texture, 0..1.<br>
	 * 
	 * @return height 0..1
	 */
	public float getHeight01()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getHeight();
	}
	
	
	/**
	 * Get the width of the texture, 0..1.<br>
	 * 
	 * @return width 0..1
	 */
	public float getWidth01()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getWidth();
	}
	
	
	/**
	 * @return OpenGL texture ID
	 */
	public int getTextureID()
	{
		if (!ensureLoaded()) return -1;
		
		return backingTexture.getTextureID();
	}
}

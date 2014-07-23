package mightypork.gamecore.backend.lwjgl;


import java.io.IOException;

import mightypork.gamecore.resources.TextureBasedResource;
import mightypork.gamecore.resources.textures.*;
import mightypork.utils.annotations.Alias;
import mightypork.utils.files.FileUtils;
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
@TextureBasedResource
public class SlickLazyTexture extends LazyTexture {
	
	private org.newdawn.slick.opengl.Texture backingTexture;
	private boolean alpha;
	private boolean alphal;
	
	/**
	 * @param resourcePath resource path
	 */
	public SlickLazyTexture(String resourcePath) {
		super(resourcePath);
	}
	
	@Override
	protected synchronized void loadResource(String path)
	{
		try {
			final String ext = FileUtils.getExtension(path).toUpperCase();
			
			final Texture texture = TextureLoader.getTexture(ext, FileUtils.getResource(path), false, filter.num);
			
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
	
	
	@Override
	public void bind()
	{
		if (!ensureLoaded()) return;
		
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureID());
		
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap.num);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter.num);
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
	public float getHeight01()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getHeight();
	}
	
	
	@Override
	public float getWidth01()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getWidth();
	}
	
	
	@Override
	public void destroy()
	{
		if (!isLoaded()) return;
		
		backingTexture.release();
	}
	
	
	@Override
	public int getTextureID()
	{
		if (!ensureLoaded()) return -1;
		
		return backingTexture.getTextureID();
	}
}

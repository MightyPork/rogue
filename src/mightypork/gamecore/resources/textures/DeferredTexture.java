package mightypork.gamecore.resources.textures;


import mightypork.gamecore.loading.BaseDeferredResource;
import mightypork.gamecore.loading.MustLoadInMainThread;
import mightypork.gamecore.render.Render;
import mightypork.utils.logging.LoggedName;
import mightypork.utils.math.coord.Rect;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


/**
 * Deferred texture
 * 
 * @author MightyPork
 */
@MustLoadInMainThread
@LoggedName(name = "Texture")
public class DeferredTexture extends BaseDeferredResource implements FilteredTexture {
	
	private Texture backingTexture;
	private Filter filter_min = Filter.LINEAR;
	private Filter filter_mag = Filter.LINEAR;
	private Wrap wrap = Wrap.CLAMP;
	
	
	public DeferredTexture(String resourcePath) {
		super(resourcePath);
	}
	
	
	public TxQuad getQuad(Rect rect)
	{
		return new TxQuad(this, rect);
	}
	
	
	@Override
	protected void loadResource(String path)
	{
		backingTexture = Render.loadTexture(path);
	}
	
	
	@Override
	public boolean hasAlpha()
	{
		if (!ensureLoaded()) return false;
		
		return backingTexture.hasAlpha();
	}
	
	
	public void bindRaw()
	{
		if (!ensureLoaded()) return;
		
		backingTexture.bind();
	}
	
	
	@Override
	public void bind()
	{
		if (!ensureLoaded()) return;
		
		backingTexture.bind();
		
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap.num);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter_min.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter_mag.num);
		
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
	public String getTextureRef()
	{
		if (!ensureLoaded()) return null;
		
		return backingTexture.getTextureRef();
	}
	
	
	@Override
	public float getHeight()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getHeight();
	}
	
	
	@Override
	public float getWidth()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getWidth();
	}
	
	
	@Override
	public int getTextureHeight()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getTextureHeight();
	}
	
	
	@Override
	public int getTextureWidth()
	{
		if (!ensureLoaded()) return 0;
		
		return backingTexture.getTextureWidth();
	}
	
	
	@Override
	public void release()
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
	
	
	@Override
	public byte[] getTextureData()
	{
		if (!ensureLoaded()) return null;
		
		return backingTexture.getTextureData();
	}
	
	
	@Override
	public void setTextureFilter(int textureFilter)
	{
		if (!ensureLoaded()) return;
		
		backingTexture.setTextureFilter(textureFilter);
	}
	
	
	@Override
	public void destroy()
	{
		release();
	}
	
	
	@Override
	public void setFilter(Filter filterMin, Filter filterMag)
	{
		this.filter_min = filterMin;
		this.filter_mag = filterMag;
	}
	
	
	@Override
	public void setWrap(Wrap wrapping)
	{
		this.wrap = wrapping;
	}
	
	
	@Override
	public void setFilter(Filter filter)
	{
		setFilter(filter, filter);
	}
	
}

package mightypork.gamecore.render.textures;


import mightypork.gamecore.loading.DeferredResource;
import mightypork.gamecore.loading.MustLoadInMainThread;
import mightypork.gamecore.render.Render;
import mightypork.utils.annotations.Alias;
import mightypork.utils.math.constraints.rect.Rect;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


/**
 * Deferred texture
 * 
 * @author MightyPork
 */
@MustLoadInMainThread
@Alias(name = "Texture")
public class DeferredTexture extends DeferredResource implements FilteredTexture {
	
	private Texture backingTexture;
	private FilterMode filter_min = FilterMode.LINEAR;
	private FilterMode filter_mag = FilterMode.NEAREST;
	private WrapMode wrap = WrapMode.CLAMP;
	
	
	/**
	 * @param resourcePath resource path
	 */
	public DeferredTexture(String resourcePath) {
		super(resourcePath);
	}
	
	
	/**
	 * Get a quad from this texture of given position/size
	 * 
	 * @param rect quad rect
	 * @return the quad
	 */
	public TxQuad getQuad(Rect rect)
	{
		return new TxQuad(this, rect);
	}
	
	
	@Override
	protected synchronized void loadResource(String path)
	{
		backingTexture = Render.loadTexture(path);
	}
	
	
	@Override
	public boolean hasAlpha()
	{
		if (!ensureLoaded()) return false;
		
		return backingTexture.hasAlpha();
	}
	
	
	/**
	 * Bind without adjusting parameters
	 */
	public void bindRaw()
	{
		if (!ensureLoaded()) return;
		
		backingTexture.bind();
	}
	
	
	/**
	 * Bind and adjust parameters (filter, wrap)
	 */
	@Override
	public void bind()
	{
		if (!ensureLoaded()) return;
		
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap.num);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter_min.num);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter_mag.num);
		
		bindRaw();
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
	public void setFilter(FilterMode filterMin, FilterMode filterMag)
	{
		this.filter_min = filterMin;
		this.filter_mag = filterMag;
	}
	
	
	@Override
	public void setWrap(WrapMode wrapping)
	{
		this.wrap = wrapping;
	}
	
	
	@Override
	public void setFilter(FilterMode filter)
	{
		setFilter(filter, filter);
	}
	
}

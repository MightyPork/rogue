package mightypork.gamecore.resources.textures;


import mightypork.dynmath.rect.Rect;
import mightypork.gamecore.logging.LogAlias;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.BaseLazyResource;
import mightypork.gamecore.resources.TextureBasedResource;

import org.lwjgl.opengl.GL11;


/**
 * Deferred texture
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@LogAlias(name = "Texture")
@TextureBasedResource
public class LazyTexture extends BaseLazyResource implements GLTexture {
	
	public static LazyTexture lastBind = null;
	
	private org.newdawn.slick.opengl.Texture backingTexture;
	private FilterMode filter = FilterMode.NEAREST;
	private WrapMode wrap = WrapMode.CLAMP;
	private boolean alpha;
	private boolean alphal;
	
	
	/**
	 * @param resourcePath resource path
	 */
	public LazyTexture(String resourcePath)
	{
		super(resourcePath);
	}
	
	
	@Override
	public TxQuad makeQuad(Rect uvs)
	{
		return new TxQuad(this, uvs);
	}
	
	
	@Override
	protected synchronized void loadResource(String path)
	{
		backingTexture = Render.loadSlickTexture(path, filter);
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
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		if (lastBind != this) {
			lastBind = this;
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureID());
			
			GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap.num);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap.num);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter.num);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter.num);
			
		}
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
	
	
	@Override
	public void setFilter(FilterMode filterMin)
	{
		this.filter = filterMin;
	}
	
	
	@Override
	public void setWrap(WrapMode wrapping)
	{
		this.wrap = wrapping;
	}
	
	
	@Override
	public QuadGrid grid(int x, int y)
	{
		return new QuadGrid(this, x, y);
	}
}

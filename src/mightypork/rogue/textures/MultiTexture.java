package mightypork.rogue.textures;


import mightypork.utils.math.coord.Rect;

import org.newdawn.slick.opengl.Texture;


public class MultiTexture implements Texture {
	
	private Texture backingTexture;
	private String resourcePath;
	
	
	public MultiTexture(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	
	public TxQuad getQuad(Rect rect)
	{
		return new TxQuad(this, rect);
	}
	
	
	/**
	 * Attempt to load the texture.
	 */
	private void load()
	{
		if (backingTexture == null) {
			backingTexture = Render.loadTexture(resourcePath);
		}
	}
	
	
	@Override
	public boolean hasAlpha()
	{
		load();
		return backingTexture.hasAlpha();
	}
	
	
	@Override
	public String getTextureRef()
	{
		load();
		return backingTexture.getTextureRef();
	}
	
	
	@Override
	public void bind()
	{
		load();
		backingTexture.bind();
	}
	
	
	@Override
	public int getImageHeight()
	{
		load();
		return backingTexture.getImageHeight();
	}
	
	
	@Override
	public int getImageWidth()
	{
		load();
		return backingTexture.getImageWidth();
	}
	
	
	@Override
	public float getHeight()
	{
		load();
		return backingTexture.getHeight();
	}
	
	
	@Override
	public float getWidth()
	{
		load();
		return backingTexture.getWidth();
	}
	
	
	@Override
	public int getTextureHeight()
	{
		load();
		return backingTexture.getTextureHeight();
	}
	
	
	@Override
	public int getTextureWidth()
	{
		load();
		return backingTexture.getTextureWidth();
	}
	
	
	@Override
	public void release()
	{
		if (backingTexture == null) return;
		backingTexture.release();
	}
	
	
	@Override
	public int getTextureID()
	{
		load();
		return backingTexture.getTextureID();
	}
	
	
	@Override
	public byte[] getTextureData()
	{
		load();
		return backingTexture.getTextureData();
	}
	
	
	@Override
	public void setTextureFilter(int textureFilter)
	{
		load();
		backingTexture.setTextureFilter(textureFilter);
	}
	
}

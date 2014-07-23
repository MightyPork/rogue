package mightypork.gamecore.resources.textures;


import mightypork.gamecore.resources.BaseLazyResource;
import mightypork.gamecore.resources.TextureBasedResource;
import mightypork.utils.annotations.Alias;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Deferred texture (to be extended by backend texture)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Texture")
@TextureBasedResource
public abstract class LazyTexture extends BaseLazyResource implements ITexture {
	
	protected FilterMode filter = FilterMode.NEAREST;
	protected WrapMode wrap = WrapMode.CLAMP;
	
	
	/**
	 * @param resourcePath resource path
	 */
	public LazyTexture(String resourcePath) {
		super(resourcePath);
	}
	
	
	@Override
	public TxQuad makeQuad(Rect uvs)
	{
		return new TxQuad(this, uvs);
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

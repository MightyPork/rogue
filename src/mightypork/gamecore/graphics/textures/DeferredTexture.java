package mightypork.gamecore.graphics.textures;


import mightypork.gamecore.resources.BaseDeferredResource;
import mightypork.gamecore.resources.loading.MustLoadInRenderingContext;
import mightypork.utils.annotations.Alias;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Deferred texture (to be extended by backend texture)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Texture")
@MustLoadInRenderingContext
public abstract class DeferredTexture extends BaseDeferredResource implements ITexture {
	
	protected FilterMode filter = FilterMode.NEAREST;
	protected WrapMode wrap = WrapMode.CLAMP;
	
	
	/**
	 * @param resourcePath resource path
	 */
	public DeferredTexture(String resourcePath) {
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

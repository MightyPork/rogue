package mightypork.gamecore.gui.components;


import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.rect.*;
import mightypork.utils.math.constraints.rect.caching.AbstractRectCache;
import mightypork.utils.math.constraints.rect.caching.RectCache;
import mightypork.utils.math.constraints.rect.proxy.RectBound;
import mightypork.utils.math.constraints.rect.proxy.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class SimplePainter extends AbstractRectCache implements PluggableRenderable {
	
	private RectCache source;
	
	
	@Override
	public Rect getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		this.source = new RectBoundAdapter(rect).cached();
	}
	
	
	@Override
	public Rect getCacheSource()
	{
		return source;
	}
	
	
	@Override
	public abstract void render();
	
	
	/**
	 * Called after constraint was changed; contained constraints can now poll too.
	 */
	@Override
	@DefaultImpl
	public void onChange()
	{
	}
}

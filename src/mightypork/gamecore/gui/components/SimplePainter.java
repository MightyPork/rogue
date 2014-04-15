package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.rect.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class SimplePainter extends RectBoundAdapter implements PluggableRenderable {
	
	@Override
	public abstract void render();
	
	
	@Override
	public Rect getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		super.setRect(rect);
	}
}

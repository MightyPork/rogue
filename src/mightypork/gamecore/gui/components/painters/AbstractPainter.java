package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class AbstractPainter extends RectBoundAdapter implements PluggableRenderable {
	
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

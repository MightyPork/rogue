package mightypork.gamecore.gui.renderers;


import mightypork.utils.math.constraints.ContextAdapter;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class PluggableRenderer extends ContextAdapter implements PluggableRenderable {
	
	@Override
	public abstract void render();
	
	
	@Override
	public Rect getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setContext(RectConstraint rect)
	{
		super.setContext(rect);
	}
}

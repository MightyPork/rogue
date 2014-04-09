package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.constraints.ContextAdapter;
import mightypork.gamecore.gui.constraints.RectConstraint;
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

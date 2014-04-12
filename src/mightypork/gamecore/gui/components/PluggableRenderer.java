package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.ContextAdapter;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.rect.RectView;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class PluggableRenderer extends ContextAdapter implements PluggableRenderable {
	
	@Override
	public abstract void render();
	
	
	@Override
	public RectView getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setContext(RectConstraint rect)
	{
		super.setContext(rect);
	}
}

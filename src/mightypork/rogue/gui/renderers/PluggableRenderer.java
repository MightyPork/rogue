package mightypork.rogue.gui.renderers;


import mightypork.rogue.render.Renderable;
import mightypork.utils.math.constraints.ContextAdapter;
import mightypork.utils.math.constraints.RectEvaluable;
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
	public void setContext(RectEvaluable rect)
	{
		super.setContext(rect);
	}
}

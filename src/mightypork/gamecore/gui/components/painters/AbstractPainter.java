package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.utils.math.constraints.RectBoundAdapter;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.rect.RectView;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class AbstractPainter extends RectBoundAdapter implements PluggableRenderable {
	
	@Override
	public abstract void render();
	
	
	@Override
	public RectView getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setContext(RectBound rect)
	{
		super.setContext(rect);
	}
}

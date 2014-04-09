package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.constraints.PluggableContext;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


/**
 * Renderable that can be assigned different context
 * 
 * @author MightyPork
 */
public interface PluggableRenderable extends Renderable, PluggableContext {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setContext(RectConstraint rect);
	
}

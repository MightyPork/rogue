package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.PluggableContext;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.rect.Rect;


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

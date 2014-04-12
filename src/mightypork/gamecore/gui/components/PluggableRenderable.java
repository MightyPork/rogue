package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.PluggableContext;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.rect.RectView;


/**
 * Renderable that can be assigned different context
 * 
 * @author MightyPork
 */
public interface PluggableRenderable extends Renderable, PluggableContext {
	
	@Override
	void render();
	
	
	@Override
	RectView getRect();
	
	
	@Override
	void setContext(RectConstraint rect);
	
}

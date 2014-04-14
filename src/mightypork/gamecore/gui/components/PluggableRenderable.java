package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.PluggableRectBound;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Renderable that can be assigned different context
 * 
 * @author MightyPork
 */
public interface PluggableRenderable extends Renderable, PluggableRectBound {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setRect(RectBound rect);
	
}

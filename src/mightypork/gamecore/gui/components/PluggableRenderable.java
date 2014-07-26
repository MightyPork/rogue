package mightypork.gamecore.gui.components;


import mightypork.gamecore.graphics.Renderable;
import mightypork.utils.math.constraints.rect.PluggableRectBound;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;


/**
 * Renderable that can be assigned different context
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface PluggableRenderable extends Renderable, PluggableRectBound {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setRect(RectBound rect);
	
}

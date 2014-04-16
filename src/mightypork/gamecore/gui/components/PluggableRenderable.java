package mightypork.gamecore.gui.components;


import mightypork.gamecore.render.Renderable;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.PluggableRectBound;
import mightypork.util.constraints.rect.proxy.RectBound;


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

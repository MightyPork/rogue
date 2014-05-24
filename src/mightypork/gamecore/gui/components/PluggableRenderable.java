package mightypork.gamecore.gui.components;


import mightypork.dynmath.rect.Rect;
import mightypork.dynmath.rect.proxy.PluggableRectBound;
import mightypork.dynmath.rect.proxy.RectBound;
import mightypork.gamecore.render.Renderable;


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

package mightypork.gamecore.gui.components;


import mightypork.dynmath.rect.PluggableRectBound;
import mightypork.dynmath.rect.Rect;
import mightypork.dynmath.rect.RectBound;
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

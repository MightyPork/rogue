package mightypork.gamecore.gui.components;


import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.PluggableRectBound;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


/**
 * Renderable that can be assigned different context
 * 
 * @author Ondřej Hruška
 */
public interface PluggableRenderable extends Renderable, PluggableRectBound {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setRect(RectBound rect);
	
}

package mightypork.gamecore.gui.renderers;


import mightypork.gamecore.render.Renderable;
import mightypork.utils.math.constraints.PluggableContext;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


public interface PluggableRenderable extends Renderable, PluggableContext {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setContext(RectConstraint rect);
	
}

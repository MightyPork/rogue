package mightypork.rogue.gui.renderers;


import mightypork.rogue.render.Renderable;
import mightypork.utils.math.constraints.PluggableContext;
import mightypork.utils.math.constraints.RectEvaluable;
import mightypork.utils.math.coord.Rect;


public interface PluggableRenderable extends Renderable, PluggableContext {
	
	@Override
	void render();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setContext(RectEvaluable rect);
	
}

package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Rect;


public interface PluggableContext extends RectEvaluable {
	
	abstract void setContext(RectEvaluable rect);
	
	
	@Override
	abstract Rect getRect();
	
}

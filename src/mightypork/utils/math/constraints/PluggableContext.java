package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Rect;


public interface PluggableContext extends RectConstraint {
	
	abstract void setContext(RectConstraint rect);
	
	
	@Override
	abstract Rect getRect();
	
}

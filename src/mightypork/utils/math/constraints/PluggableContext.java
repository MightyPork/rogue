package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.Rect;


/**
 * Interface for constraints that can be assigned context
 * 
 * @author MightyPork
 */
public interface PluggableContext extends RectConstraint {
	
	/**
	 * @param rect context to set
	 */
	abstract void setContext(RectConstraint rect);
	
	
	@Override
	abstract Rect getRect();
	
}

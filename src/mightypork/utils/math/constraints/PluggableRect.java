package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.RectValue;


/**
 * Interface for constraints that can be assigned context
 * 
 * @author MightyPork
 */
public interface PluggableRect extends RectConstraint {
	
	/**
	 * @param rect context to set
	 */
	abstract void setContext(RectConstraint rect);
	
	
	@Override
	abstract RectValue getRect();
	
}

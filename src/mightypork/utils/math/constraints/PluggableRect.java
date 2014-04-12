package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.RectView;


/**
 * Interface for constraints that can be assigned context
 * 
 * @author MightyPork
 */
public interface PluggableRect extends RectBound {
	
	/**
	 * @param rect context to set
	 */
	abstract void setContext(RectBound rect);
	
	
	@Override
	abstract RectView getRect();
	
}

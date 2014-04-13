package mightypork.utils.math.constraints;


/**
 * Interface for constraints that can be assigned context
 * 
 * @author MightyPork
 */
public interface PluggableRectBound extends RectBound {
	
	/**
	 * @param rect context to set
	 */
	abstract void setContext(RectBound rect);
	
}

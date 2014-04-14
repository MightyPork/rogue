package mightypork.utils.math.constraints;


/**
 * Pluggable rect bound
 * 
 * @author MightyPork
 */
public interface PluggableRectBound extends RectBound {
	
	/**
	 * @param rect context to set
	 */
	abstract void setRect(RectBound rect);
	
}

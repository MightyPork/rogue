package mightypork.gamecore.util.math.constraints.rect.proxy;


/**
 * Pluggable rect bound
 * 
 * @author Ondřej Hruška
 */
public interface PluggableRectBound extends RectBound {
	
	/**
	 * @param rect context to set
	 */
	abstract void setRect(RectBound rect);
	
}

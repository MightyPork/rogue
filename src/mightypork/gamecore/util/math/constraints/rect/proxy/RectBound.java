package mightypork.gamecore.util.math.constraints.rect.proxy;


import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Rect constraint (ie. region)
 * 
 * @author Ondřej Hruška
 */
public interface RectBound {
	
	/**
	 * @return rect region
	 */
	Rect getRect();
}

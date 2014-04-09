package mightypork.gamecore.gui.constraints;


import mightypork.utils.math.coord.Rect;


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

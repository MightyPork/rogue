package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.render.Renderable;
import mightypork.util.math.constraints.rect.proxy.RectBound;


/**
 * Game screen
 * 
 * @author MightyPork
 */
public interface Screen extends Renderable, RectBound, AppAccess {
	
	/**
	 * Prepare for being shown
	 * 
	 * @param shown true to show, false to hide
	 */
	void setActive(boolean shown);
	
	
	/**
	 * @return true if screen is the current screen
	 */
	boolean isActive();
	
	
	/**
	 * @return screen identifier to be used for requests.
	 */
	String getName();
	
}

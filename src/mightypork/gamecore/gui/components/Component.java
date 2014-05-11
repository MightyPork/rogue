package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.gui.Hideable;


/**
 * Basic UI component interface
 * 
 * @author MightyPork
 */
public interface Component extends Enableable, Hideable, PluggableRenderable {
	
	/**
	 * Render the component, if it is visible.
	 */
	@Override
	void render();
	
	
	/**
	 * The bounding rect was changed. The component should now update any cached
	 * constraints derived from it.
	 */
	void updateLayout();
}

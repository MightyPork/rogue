package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.Hideable;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBound;


/**
 * Basic UI component interface
 * 
 * @author MightyPork
 */
public interface Component extends Hideable, PluggableRenderable {
	
	/**
	 * Set visible. When not visible, the component should not render.
	 */
	@Override
	void setVisible(boolean yes);
	
	
	@Override
	boolean isVisible();
	
	
	@Override
	Rect getRect();
	
	
	@Override
	void setRect(RectBound rect);
	
	
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

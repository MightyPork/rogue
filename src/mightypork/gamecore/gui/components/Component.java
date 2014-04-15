package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.bus.clients.ToggleableClient;
import mightypork.gamecore.control.interf.Enableable;
import mightypork.gamecore.gui.Hideable;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * UI component interface
 * 
 * @author MightyPork
 */
public interface Component extends Hideable, PluggableRenderable, Enableable, ToggleableClient {
	
	/**
	 * Enable the component. This includes listening to event bus, and any
	 * event-related actions.
	 */
	@Override
	void enable(boolean yes);
	
	
	@Override
	boolean isEnabled();
	
	
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
	
	
	@Override
	void render();
	
	
	@Override
	public boolean isListening();
}

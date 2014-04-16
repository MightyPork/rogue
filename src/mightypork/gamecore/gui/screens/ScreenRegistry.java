package mightypork.gamecore.gui.screens;


import java.util.HashMap;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppModule;
import mightypork.gamecore.control.events.ScreenRequestEvent;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.logging.Log;


/**
 * Game screens holder; Takes care of rendering and screen requests.
 * 
 * @author MightyPork
 */
public class ScreenRegistry extends AppModule implements ScreenRequestEvent.Listener, Renderable {
	
	private final HashMap<String, Screen> screens = new HashMap<>();
	private volatile Screen active = null;
	
	
	/**
	 * @param app app access
	 */
	public ScreenRegistry(AppAccess app) {
		super(app);
	}
	
	
	/**
	 * Add a screen
	 * 
	 * @param screen added screen
	 */
	public void add(Screen screen)
	{
		screens.put(screen.getName(), screen);
		addChildClient(screen);
	}
	
	
	@Override
	public void showScreen(String key)
	{
		Log.f3("Request to show screen \"" + key + "\"");
		
		final Screen toshow = screens.get(key);
		if (toshow == null) throw new RuntimeException("Screen " + key + " not defined.");
		
		if (active != null) active.setActive(false);
		
		toshow.setActive(true);
		
		active = toshow;
	}
	
	
	@Override
	public void render()
	{
		if (active != null) active.render();
	}
	
	
	@Override
	@DefaultImpl
	protected void deinit()
	{
		//
	}
	
}

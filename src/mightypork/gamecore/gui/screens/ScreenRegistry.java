package mightypork.gamecore.gui.screens;


import java.util.HashMap;

import mightypork.gamecore.AppAccess;
import mightypork.gamecore.control.Subsystem;
import mightypork.gamecore.control.bus.events.ScreenRequestEvent;
import mightypork.gamecore.render.Renderable;
import mightypork.utils.logging.Log;


public class ScreenRegistry extends Subsystem implements ScreenRequestEvent.Listener, Renderable {
	
	private final HashMap<String, Screen> screens = new HashMap<String, Screen>();
	private Screen active = null;
	
	
	public ScreenRegistry(AppAccess app) {
		super(app);
	}
	
	
	public void add(Screen screen)
	{
		screens.put(screen.getId(), screen);
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
	protected void deinit()
	{
		// no impl
	}
	
}

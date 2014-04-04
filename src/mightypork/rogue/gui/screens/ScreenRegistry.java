package mightypork.rogue.gui.screens;


import java.util.HashMap;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ScreenRequestEvent;


public class ScreenRegistry extends Subsystem implements ScreenRequestEvent.Listener {
	
	private HashMap<String, Screen> screens = new HashMap<String, Screen>();
	private Screen active = null;
	
	
	public ScreenRegistry(AppAccess app) {
		super(app);
	}
	
	
	public void add(String key, Screen screen)
	{
		screens.put(key, screen);
		addChildClient(screen);
	}
	
	
	@Override
	public void showScreen(String key)
	{
		Screen toshow = screens.get(key);
		if (toshow == null) throw new RuntimeException("Screen " + key + " not defined.");
		
		if (active != null) active.setActive(false);
		
		toshow.setActive(true);
		
		active = toshow;
	}
	
	
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

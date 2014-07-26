package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.ScreenRequestListener;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.gamecore.gui.events.ViewportChangeListener;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.logging.Log;


/**
 * Game screens holder; Takes care of rendering and screen requests.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ScreenRegistry extends BusNode implements ScreenRequestListener, ViewportChangeListener, Renderable {
	
	private final Map<String, Screen> screens = new HashMap<>();
	private final Collection<Overlay> overlays = new TreeSet<>();
	private volatile Screen active = null;
	
	
	/**
	 * Add a screen
	 * 
	 * @param name screen key for calling
	 * @param screen added screen
	 */
	public void addScreen(String name, Screen screen)
	{
		screens.put(name, screen);
		addChildClient(screen);
	}
	
	
	/**
	 * Add an overlay
	 * 
	 * @param overlay added overlay
	 */
	public void addOverlay(Overlay overlay)
	{
		overlays.add(overlay);
		addChildClient(overlay);
	}
	
	
	@Override
	public void showScreen(String key)
	{
		Log.f3("Request to show screen \"" + key + "\"");
		
		// find screen to show
		final Screen toShow = screens.get(key);
		if (toShow == null) {
			throw new RuntimeException("Screen " + key + " not defined.");
		}
		
		// deactivate last screen
		if (active != null) {
			active.setActive(false);
		}
		
		// activate new screen
		toShow.setActive(true);
		
		active = toShow;
		
		fireLayoutUpdateEvent();
	}
	
	
	@Override
	public void render()
	{
		if (active != null) {
			active.render();
			
			for (final Overlay overlay : overlays) {
				if (overlay.isVisible()) overlay.render();
			}
		}
	}
	
	
	@Override
	public void onViewportChanged(ViewportChangeEvent event)
	{
		if (active != null) fireLayoutUpdateEvent();
	}
	
	
	private void fireLayoutUpdateEvent()
	{
		App.bus().sendDirectToChildren(this, new LayoutChangeEvent());
	}
	
}

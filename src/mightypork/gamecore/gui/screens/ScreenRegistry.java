package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.AppModule;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.ScreenRequestListener;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.gamecore.gui.events.ViewportChangeListener;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;


/**
 * Game screens holder; Takes care of rendering and screen requests.
 * 
 * @author MightyPork
 */
public class ScreenRegistry extends AppModule implements ScreenRequestListener, ViewportChangeListener, Renderable {
	
	private final Map<String, Screen> screens = new HashMap<>();
	private final Collection<Overlay> overlays = new TreeSet<>();
	private volatile Screen active = null;
	
	
	/**
	 * @param app app access
	 */
	public ScreenRegistry(AppAccess app)
	{
		super(app);
	}
	
	
	/**
	 * Add a screen
	 * 
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
		if (toShow == null) { throw new RuntimeException("Screen " + key + " not defined."); }
		
		// deactivate last screen
		if (active != null) {
			active.setActive(false);
		}
		
		// activate new screen
		toShow.setActive(true);
		
		active = toShow;
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
	@DefaultImpl
	protected void deinit()
	{
		//
	}
	
	
	@Override
	public void onViewportChanged(ViewportChangeEvent event)
	{
		getEventBus().sendDirectToChildren(this, new LayoutChangeEvent());
	}
	
}

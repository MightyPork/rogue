package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.eventbus.clients.DelegatingClient;


/**
 * Screen with multiple instances of {@link ScreenLayer}
 * 
 * @author MightyPork
 */
public abstract class LayeredScreen extends Screen {
	
	/**
	 * Wrapper for delegating client, to use custom client ordering.
	 * 
	 * @author MightyPork
	 */
	private class LayersClient implements DelegatingClient {
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Collection getChildClients()
		{
			return layersByEventPriority;
		}
		
		
		@Override
		public boolean doesDelegate()
		{
			return true;
		}
		
	}
	
	private final Collection<ScreenLayer> layersByZIndex = new TreeSet<>(new Comparator<Overlay>() {
		
		@Override
		public int compare(Overlay o1, Overlay o2)
		{
			return o1.getZIndex() - o2.getZIndex();
		}
		
	});
	
	private final Collection<ScreenLayer> layersByEventPriority = new TreeSet<>(new Comparator<Overlay>() {
		
		@Override
		public int compare(Overlay o1, Overlay o2)
		{
			return o2.getEventPriority() - o1.getEventPriority();
		}
		
	});
	
	private final LayersClient layersClient = new LayersClient();
	
	
	/**
	 * @param app app access
	 */
	public LayeredScreen(AppAccess app)
	{
		super(app);
		addChildClient(layersClient);
	}
	
	
	@Override
	protected void renderScreen()
	{
		for (final ScreenLayer layer : layersByZIndex) {
			if (layer.isVisible()) layer.render();
		}
	}
	
	
	/**
	 * Add a layer to the screen.
	 * 
	 * @param layer
	 */
	protected void addLayer(ScreenLayer layer)
	{
		this.layersByZIndex.add(layer);
		this.layersByEventPriority.add(layer);
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		for (final ScreenLayer layer : layersByEventPriority) {
			layer.onScreenEnter();
		}
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		for (final ScreenLayer layer : layersByEventPriority) {
			layer.onScreenLeave();
		}
	}
	
}

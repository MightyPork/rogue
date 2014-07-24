package mightypork.gamecore.gui.screens;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mightypork.utils.eventbus.clients.DelegatingClient;


/**
 * Screen with multiple instances of {@link ScreenLayer}
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class LayeredScreen extends Screen {
	
	/**
	 * Wrapper for delegating client, to use custom client ordering.
	 * 
	 * @author Ondřej Hruška (MightyPork)
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
	
	private final List<ScreenLayer> layersByZIndex = new ArrayList<>();
	private final List<ScreenLayer> layersByEventPriority = new ArrayList<>();
	
	private final LayersClient layersClient = new LayersClient();
	
	
	public LayeredScreen() {
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
		
		Collections.sort(layersByEventPriority, new Comparator<Overlay>() {
			
			@Override
			public int compare(Overlay o1, Overlay o2)
			{
				return o2.getEventPriority() - o1.getEventPriority();
			}
			
		});
		
		Collections.sort(layersByZIndex, new Comparator<Overlay>() {
			
			@Override
			public int compare(Overlay o1, Overlay o2)
			{
				return o1.getZIndex() - o2.getZIndex();
			}
			
		});
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

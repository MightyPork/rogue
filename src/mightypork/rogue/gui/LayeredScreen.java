package mightypork.rogue.gui;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.rogue.AppAccess;


public abstract class LayeredScreen extends Screen {
	
	private final Collection<ScreenLayer> layers = new LinkedList<ScreenLayer>();
	
	
	public LayeredScreen(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected final void renderScreen()
	{
		for (final ScreenLayer layer : layers) {
			layer.render();
		}
	}
	
	
	/**
	 * Add a layer to the screen.
	 * 
	 * @param layer
	 */
	protected final void addLayer(ScreenLayer layer)
	{
		this.layers.add(layer); // will be rendered from last to first
		addChildClient(layer); // connect to bus
	}
	
	
	/**
	 * Remove a layer
	 * 
	 * @param layer
	 */
	protected final void removeLayer(ScreenLayer layer)
	{
		this.layers.remove(layer);
		removeChildClient(layer);
	}
	
}

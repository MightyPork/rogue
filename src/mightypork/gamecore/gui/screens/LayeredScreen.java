package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.TreeSet;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.render.Render;


public abstract class LayeredScreen extends Screen {
	
	private final Collection<ScreenLayer> layers = new TreeSet<ScreenLayer>();
	
	
	public LayeredScreen(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected void renderScreen()
	{
		for (final ScreenLayer layer : layers) {
			Render.pushState();
			layer.render();
			Render.popState();
		}
	}
	
	
	/**
	 * Add a layer to the screen.
	 * 
	 * @param layer
	 */
	protected void addLayer(ScreenLayer layer)
	{
		this.layers.add(layer);
		addChildClient(layer);
	}
	
	
	/**
	 * Remove a layer
	 * 
	 * @param layer
	 */
	protected void removeLayer(ScreenLayer layer)
	{
		this.layers.remove(layer);
		removeChildClient(layer);
	}
	
}

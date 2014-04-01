package mightypork.rogue.display;


import java.util.LinkedList;

import mightypork.rogue.AppAccess;
import mightypork.utils.control.timing.Updateable;


public abstract class LayeredScreen extends Screen {
	
	private final LinkedList<ScreenLayer> layers = new LinkedList<ScreenLayer>();
	
	
	public LayeredScreen(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected abstract void deinitScreen();
	
	
	@Override
	protected abstract void onScreenEnter();
	
	
	@Override
	protected abstract void onScreenLeave();
	
	
	@Override
	protected final void renderScreen()
	{
		for (ScreenLayer layer : layers) {
			layer.render();
		}
	}
	
	
	/**
	 * Update screen. Layers should implement {@link Updateable} to receive
	 * updates directly from bus.
	 */
	@Override
	protected abstract void updateScreen(double delta);
	
	
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

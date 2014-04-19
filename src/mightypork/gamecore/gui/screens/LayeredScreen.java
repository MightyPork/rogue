package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.TreeSet;

import mightypork.gamecore.control.AppAccess;


/**
 * Screen with multiple instances of {@link ScreenLayer}
 * 
 * @author MightyPork
 */
public abstract class LayeredScreen extends BaseScreen {
	
	private final Collection<ScreenLayer> layers = new TreeSet<>();
	
	
	/**
	 * @param app app access
	 */
	public LayeredScreen(AppAccess app)
	{
		super(app);
	}
	
	
	@Override
	protected void renderScreen()
	{
		for (final ScreenLayer layer : layers) {
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
		this.layers.add(layer);
		addChildClient(layer);
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		for (final ScreenLayer layer : layers) {
			layer.onScreenEnter();
		}
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		for (final ScreenLayer layer : layers) {
			layer.onScreenLeave();
		}
	}
	
}

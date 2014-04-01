package mightypork.rogue.display;


import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.AppAccess;
import mightypork.rogue.display.rendering.ScreenLayer;


public abstract class LayeredScreen extends Screen {
	
	private List<ScreenLayer> layers = new ArrayList<ScreenLayer>();
	
	
	public LayeredScreen(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected abstract void initScreen();
	
	
	@Override
	protected abstract void deinitScreen();
	
	
	@Override
	protected abstract void onScreenEnter();
	
	
	@Override
	protected abstract void onScreenLeave();
	
	
	@Override
	protected void renderScreen()
	{
		// in reverse order (topmost added last)
		for (int i = layers.size() - 1; i >= 0; i--) {
			layers.get(i).render();
		}
	}
	
	
	@Override
	protected void updateScreen(double delta)
	{
		// no impl
	}
	
	
	protected void addLayer(ScreenLayer layer)
	{
		this.layers.add(layer);
	}
	
	
	protected void removeLayer(ScreenLayer layer)
	{
		this.layers.remove(layer);
	}
	
}

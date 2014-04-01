package mightypork.rogue.display.rendering;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.DelegatingBusClient;


public abstract class ScreenLayer extends DelegatingBusClient implements Renderable {
	
	public ScreenLayer(AppAccess app) {
		super(app, true);
	}
	
	
	@Override
	public abstract void render();
	
}

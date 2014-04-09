package mightypork.rogue.screens.test_bouncyboxes;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.events.ScreenRequestEvent;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.screens.LayerFps;


public class ScreenTestBouncy extends LayeredScreen {
	
	private final LayerBouncyBoxes layer;
	
	
	public ScreenTestBouncy(AppAccess app) {
		super(app);
		
		layer = new LayerBouncyBoxes(this);
		
		addLayer(new LayerFps(this));
		
		addLayer(layer);
		
		bindKeyStroke(new KeyStroke(Keys.KEY_C), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ScreenRequestEvent("test.cat"));
			}
		});
	}
	
	
	@Override
	public String getName()
	{
		return "test.bouncy";
	}
	
}

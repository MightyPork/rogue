package mightypork.rogue.gui.screens.test_bouncyboxes;


import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.events.ScreenRequestEvent;
import mightypork.rogue.gui.screens.LayeredScreen;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.input.Keys;


public class ScreenTestBouncy extends LayeredScreen {
	
	private final LayerBouncyBoxes layer;
	
	
	public ScreenTestBouncy(AppAccess app) {
		super(app);
		
		layer = new LayerBouncyBoxes(this);
		
		addLayer(layer);
		
		bindKeyStroke(new KeyStroke(Keys.KEY_C), new Runnable() {
			
			@Override
			public void run()
			{
				bus().send(new ScreenRequestEvent("test.cat"));
			}
		});
	}
	
	
	@Override
	protected void deinitScreen()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		// no impl
	}
	
	
	@Override
	public String getId()
	{
		return "test.bouncy";
	}
	
}

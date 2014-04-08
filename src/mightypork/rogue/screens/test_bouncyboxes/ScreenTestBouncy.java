package mightypork.rogue.screens.test_bouncyboxes;


import mightypork.gamecore.AppAccess;
import mightypork.gamecore.control.bus.events.ScreenRequestEvent;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;


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
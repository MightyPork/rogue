package mightypork.rogue.gui.screens.test_cat_sound;


import mightypork.rogue.AppAccess;
import mightypork.rogue.Res;
import mightypork.rogue.bus.events.ActionRequest;
import mightypork.rogue.bus.events.ActionRequest.RequestType;
import mightypork.rogue.bus.events.ScreenRequestEvent;
import mightypork.rogue.gui.LayeredScreen;
import mightypork.rogue.input.KeyStroke;

import org.lwjgl.input.Keyboard;


public class ScreenTestCat extends LayeredScreen {
	
	LayerFlyingCat layer;
	
	
	public ScreenTestCat(AppAccess app) {
		super(app);
		
		addLayer(layer = new LayerFlyingCat(this));
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				snd().fadeOutAllLoops();
				bus().sendDelayed(new ActionRequest(RequestType.SHUTDOWN), 3);
			}
		});
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_B), new Runnable() {
			
			@Override
			public void run()
			{
				bus().send(new ScreenRequestEvent("test.bouncy"));
			}
		});
	}
	
	
	@Override
	protected void deinitScreen()
	{
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		snd().fadeOutAllLoops();
		Res.getLoop("test.wilderness").fadeIn();
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		Res.getLoop("test.wilderness").fadeOut();
	}
	
	
	@Override
	public String getId()
	{
		return "test.cat";
	}
	
}

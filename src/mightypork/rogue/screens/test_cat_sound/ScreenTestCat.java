package mightypork.rogue.screens.test_cat_sound;


import mightypork.gamecore.AppAccess;
import mightypork.gamecore.control.bus.events.ScreenRequestEvent;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Res;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


public class ScreenTestCat extends LayeredScreen {
	
	LayerFlyingCat layer;
	
	
	public ScreenTestCat(AppAccess app) {
		super(app);
		
		addLayer(layer = new LayerFlyingCat(this));
		
		bindKeyStroke(new KeyStroke(Keys.KEY_ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				snd().fadeOutAllLoops();
				bus().sendDelayed(new ActionRequest(RequestType.SHUTDOWN), 3);
			}
		});
		
		bindKeyStroke(new KeyStroke(Keys.KEY_B), new Runnable() {
			
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
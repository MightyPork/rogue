package mightypork.rogue.screens.test_cat_sound;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.events.ScreenRequestEvent;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Res;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.screens.LayerFps;


public class ScreenTestCat extends LayeredScreen {
	
	public ScreenTestCat(AppAccess app) {
		super(app);
		
		addLayer(new LayerFps(this));
		addLayer(new LayerFlyingCat(this));
		
		bindKeyStroke(new KeyStroke(Keys.KEY_ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				getSoundSystem().fadeOutAllLoops();
				getEventBus().sendDelayed(new ActionRequest(RequestType.SHUTDOWN), 3);
			}
		});
		
		bindKeyStroke(new KeyStroke(Keys.KEY_B), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ScreenRequestEvent("test.bouncy"));
			}
		});
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		getSoundSystem().fadeOutAllLoops();
		Res.getLoop("test.wilderness").fadeIn();
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		Res.getLoop("test.wilderness").fadeOut();
	}
	
	
	@Override
	public String getName()
	{
		return "test.cat";
	}
}

package mightypork.rogue.screens.test_cat_sound;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


public class ScreenTestCat extends LayeredScreen {
	
	public ScreenTestCat(AppAccess app)
	{
		super(app);
		
		addLayer(new LayerFlyingCat(this));
		
		bindKey(new KeyStroke(Keys.ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				getSoundSystem().fadeOutAllLoops();
				getEventBus().sendDelayed(new ActionRequest(RequestType.SHUTDOWN), 3);
			}
		});
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		getSoundSystem().fadeOutAllLoops();
		//Res.getLoop("test.wilderness").fadeIn();
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		//Res.getLoop("test.wilderness").fadeOut();
	}
	
	
	@Override
	public String getName()
	{
		return "test.cat";
	}
}

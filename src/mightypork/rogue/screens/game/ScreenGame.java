package mightypork.rogue.screens.game;


import java.util.Random;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.events.WorldPauseRequest;


public class ScreenGame extends LayeredScreen {
	
	
	private final Random rand = new Random();
	
	
	public ScreenGame(AppAccess app)
	{
		super(app);
		
		addLayer(new HudLayer(this));
		addLayer(new WorldLayer(this));
		
		bindKey(new KeyStroke(Keys.L_CONTROL, Keys.N), new Runnable() {
			
			@Override
			public void run()
			{
				WorldProvider.get().createWorld(rand.nextLong());
			}
		});
		
		final Runnable pauseIt = new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new WorldPauseRequest());
			}
		};
		
		//pause key
		bindKey(new KeyStroke(Keys.P), pauseIt);
		bindKey(new KeyStroke(Keys.PAUSE), pauseIt);
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		super.onScreenEnter();
		WorldProvider.get().setListening(true);
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		super.onScreenLeave();
		WorldProvider.get().setListening(false);
	}
}

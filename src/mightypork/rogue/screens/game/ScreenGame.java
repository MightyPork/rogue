package mightypork.rogue.screens.game;


import java.util.Random;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.world.WorldProvider;


public class ScreenGame extends LayeredScreen {
	
	
	private Random rand = new Random();


	public ScreenGame(AppAccess app)
	{
		super(app);
		
		addLayer(new HudLayer(this));
		addLayer(new WorldLayer(this));
		
		bindKey(new KeyStroke(Keys.N), new Runnable() {
			
			@Override
			public void run()
			{
				WorldProvider.get().createWorld(rand .nextLong());
			}
		});
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

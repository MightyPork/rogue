package mightypork.rogue.screens.gamescreen;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.Paths;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldCreator;
import mightypork.rogue.world.WorldProvider;
import mightypork.util.files.ion.Ion;


public class ScreenGame extends LayeredScreen {
	
	
	public ScreenGame(AppAccess app)
	{
		super(app);
		
		addLayer(new HudLayer(this));
		addLayer(new WorldLayer(this));
	}
	
	
	@Override
	public String getName()
	{
		return "game_screen";
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

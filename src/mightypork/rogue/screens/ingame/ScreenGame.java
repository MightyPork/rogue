package mightypork.rogue.screens.ingame;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenGame extends LayeredScreen {
	
	public ScreenGame(AppAccess app) {
		super(app);
		
		addLayer(new GameGui(this));
	}
	
	
	@Override
	public String getName()
	{
		return "game_screen";
	}
	
}

package mightypork.rogue.screens.menu;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenMainMenu extends LayeredScreen {
	
	public ScreenMainMenu(AppAccess app)
	{
		super(app);
		
		addLayer(new MenuLayer(this));
	}
}

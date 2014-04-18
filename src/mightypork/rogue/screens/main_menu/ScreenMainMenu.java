package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenMainMenu extends LayeredScreen {
	
	public ScreenMainMenu(AppAccess app) {
		super(app);
		
		addLayer(new MenuLayer(this));
	}
	
	
	@Override
	public String getName()
	{
		return "main_menu";
	}
	
}

package mightypork.rogue.screens.test_bouncyboxes;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenTestBouncy extends LayeredScreen {
	
	public ScreenTestBouncy(AppAccess app) {
		super(app);
		
		addLayer(new LayerBouncyBoxes(this));
	}
	
}

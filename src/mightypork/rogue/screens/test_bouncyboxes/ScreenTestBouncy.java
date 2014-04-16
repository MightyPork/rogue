package mightypork.rogue.screens.test_bouncyboxes;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenTestBouncy extends LayeredScreen {
	
	private final LayerBouncyBoxes layer;
	
	
	public ScreenTestBouncy(AppAccess app) {
		super(app);
		
		layer = new LayerBouncyBoxes(this);
		
		addLayer(layer);
	}
	
	
	@Override
	public String getName()
	{
		return "test.bouncy";
	}
	
}

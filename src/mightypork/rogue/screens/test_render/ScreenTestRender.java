package mightypork.rogue.screens.test_render;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.screens.LayerFps;


public class ScreenTestRender extends LayeredScreen {
	
	public ScreenTestRender(AppAccess app) {
		super(app);
		
		addLayer(new LayerFps(this));
		addLayer(new LayerTestGradient(this));
	}
	
	
	@Override
	public String getName()
	{
		return "test.render";
	}
	
}

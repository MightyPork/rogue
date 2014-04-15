package mightypork.rogue.screens.test_render;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.screens.LayerFps;
import mightypork.rogue.screens.test_cat_sound.LayerFlyingCat;


public class ScreenTestRender extends LayeredScreen {
	
	public ScreenTestRender(AppAccess app) {
		super(app);
		
		addLayer(new LayerFps(this));
		addLayer(new LayerTestGradient(this));
		addLayer(new LayerFlyingCat(this));
	}
	
	
	@Override
	public String getName()
	{
		return "test.render";
	}
	
}

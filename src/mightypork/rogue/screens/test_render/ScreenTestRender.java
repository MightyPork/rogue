package mightypork.rogue.screens.test_render;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;


public class ScreenTestRender extends LayeredScreen {
	
	public ScreenTestRender(AppAccess app)
	{
		super(app);
		
		addLayer(new LayerTestGradient(this));
	}
	
	
	@Override
	public String getName()
	{
		return "test.render";
	}
	
}

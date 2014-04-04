package mightypork.rogue.gui.screens.screenBouncy;


import mightypork.rogue.AppAccess;
import mightypork.rogue.gui.screens.LayeredScreen;
import mightypork.rogue.input.KeyStroke;

import org.lwjgl.input.Keyboard;


public class ScreenTestAnim extends LayeredScreen {
	
	private LayerBouncyBoxes layer;
	
	
	public ScreenTestAnim(AppAccess app) {
		super(app);
		
		layer = new LayerBouncyBoxes(this);
		
		addLayer(layer);
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				layer.goRight();
			}
		});
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				layer.goLeft();
			}
		});
	}
	
	
	@Override
	protected void deinitScreen()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		// no impl
	}
	
	
	@Override
	protected void updateScreen(double delta)
	{
		// no impl
	}
	
}

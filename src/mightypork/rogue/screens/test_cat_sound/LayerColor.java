package mightypork.rogue.screens.test_cat_sound;


import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.util.math.color.Color;


public class LayerColor extends ScreenLayer {
	
	public LayerColor(BaseScreen screen, Color color)
	{
		super(screen);
		
		root.add(new QuadPainter(color));
	}
	
	
	@Override
	public int getZIndex()
	{
		return Integer.MIN_VALUE;
	}
	
}

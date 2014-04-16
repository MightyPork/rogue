package mightypork.rogue.screens.test_cat_sound;

import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.utils.math.color.Color;


public class LayerColor extends ScreenLayer{
	
	public LayerColor(Screen screen, Color color) {
		super(screen);
		
		root.add(new QuadPainter(color));
	}

	@Override
	public int getPriority()
	{
		return Integer.MIN_VALUE;
	}
	
}

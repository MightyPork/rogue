package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.color.Color;


public class LayerColor extends ScreenLayer {
	
	private int zIndex;


	public LayerColor(Screen screen, Color color, int zIndex)
	{
		super(screen);
		
		root.add(new QuadPainter(color));
		this.zIndex = zIndex;
	}
	
	
	@Override
	public int getZIndex()
	{
		return this.zIndex;
	}
	
}

package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.utils.math.color.Color;


public class LayerColor extends ScreenLayer {
	
	private final int zIndex;
	
	
	public LayerColor(Screen screen, Color color, int zIndex) {
		super(screen);
		
		final QuadPainter qp = new QuadPainter(color);
		qp.setRect(root);
		root.add(qp);
		this.zIndex = zIndex;
	}
	
	
	@Override
	public int getZIndex()
	{
		return this.zIndex;
	}
	
}

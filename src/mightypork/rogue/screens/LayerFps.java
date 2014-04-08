package mightypork.rogue.screens;


import mightypork.gamecore.gui.renderers.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class LayerFps extends ScreenLayer {
	
	TextPainter tp;
	private final FontRenderer fr;
	
	
	public LayerFps(Screen screen) {
		super(screen);
		
		fr = new FontRenderer(Res.getFont("default"), RGB.WHITE);
	}
	
	
	@Override
	public void render()
	{
		final Coord pos = new Coord(DisplaySystem.getWidth() - 8, 8);
		fr.draw(getDisplay().getFps() + " fps", pos, 32, Align.RIGHT);
	}
	
	
	@Override
	public int getPriority()
	{
		return Integer.MAX_VALUE;
	}
}

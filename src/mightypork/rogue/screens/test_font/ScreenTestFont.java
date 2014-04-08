package mightypork.rogue.screens.test_font;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class ScreenTestFont extends Screen {
	
	private final FontRenderer fr;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		fr = new FontRenderer(Res.getFont("default"));
	}
	
	
	@Override
	protected void renderScreen()
	{
		final String str = "O hai";
		
		final double height = getRect().getHeight() / 5D;
		final Coord space = fr.getNeededSpace(str, height);
		
		final Coord origin = getRect().getCenter().sub(space.half());
		
		fr.draw(str, origin, height, RGB.GREEN);
	}
	
	
	@Override
	public String getId()
	{
		return "test.font";
	}
	
}

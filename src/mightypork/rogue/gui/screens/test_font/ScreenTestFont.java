package mightypork.rogue.gui.screens.test_font;


import mightypork.rogue.AppAccess;
import mightypork.rogue.Res;
import mightypork.rogue.fonts.GLFont;
import mightypork.rogue.gui.Screen;
import mightypork.rogue.render.Render;
import mightypork.utils.math.coord.Coord;


public class ScreenTestFont extends Screen {
	
	public ScreenTestFont(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected void deinitScreen()
	{
		//
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		//
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		//
	}
	
	
	@Override
	protected void renderScreen()
	{
		GLFont font = Res.getFont("PolygonPixel_16");
		
		String s = "It works!";
		double scale = getRect().height() / 50D;
		Render.pushState();
		Render.translate(getRect().getCenter().sub(font.getNeededSpace(s).mul(scale).half()));
		Render.scale(new Coord(scale));
		font.draw("It works!");
		Render.popState();
	}
	
	
	@Override
	public String getId()
	{
		return "test.font";
	}
	
}

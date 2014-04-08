package mightypork.rogue.gui.screens.test_font;


import mightypork.rogue.AppAccess;
import mightypork.rogue.Res;
import mightypork.rogue.fonts.FontRenderer;
import mightypork.rogue.gui.screens.Screen;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class ScreenTestFont extends Screen {
	
	private final FontRenderer fr;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		fr = new FontRenderer(Res.getFont("default"));
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
		final String str = "O hai";
		
		final double height = getRect().getHeight() / 5D;
		final Coord space = fr.getNeededSpace(str, height);
		
		final Coord origin = getRect().getCenter().sub(space.half());
		
		fr.draw(str, origin, height, RGB.GREEN);
		
//		final GLFont font = Res.getFont("");
//		
//		final String s = "It works!";
//		final double scale = getRect().height() / 50D;
//		Render.pushState();
//		Render.translate(getRect().getCenter().sub(font.getNeededSpace(s).mul(scale).half()));
//		Render.scale(new Coord(scale));
//		font.draw("It works!");
//		Render.popState();
	}
	
	
	@Override
	public String getId()
	{
		return "test.font";
	}
	
}

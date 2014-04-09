package mightypork.rogue.screens.test_font;


import static mightypork.gamecore.gui.constraints.Constraints.*;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;


public class ScreenTestFont extends Screen {
	
	private final TextPainter tp;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		tp = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.GREEN);
		tp.setText("Hello World!");
		
		final RectConstraint strbox = _grow(_center(this), 0, _div(_height(this), 10));
		
		tp.setContext(strbox);
	}
	
	
	@Override
	protected void renderScreen()
	{
		tp.render();
	}
	
	
	@Override
	public String getName()
	{
		return "test.font";
	}
	
}

package mightypork.rogue.screens.test_font;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.renderers.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;


public class ScreenTestFont extends Screen {
	
	private final TextPainter tp;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		tp = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.GREEN);
		tp.setText("Hello World!");
		
		final RectConstraint strbox = c_grow(c_center(this), 0, c_div(c_height(this), 10));
		
		tp.setContext(strbox);
	}
	
	
	@Override
	protected void renderScreen()
	{
		tp.render();
	}
	
	
	@Override
	public String getId()
	{
		return "test.font";
	}
	
}

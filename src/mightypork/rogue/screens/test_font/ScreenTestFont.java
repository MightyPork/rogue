package mightypork.rogue.screens.test_font;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;


public class ScreenTestFont extends Screen {
	
	private final TextPainter tp;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		tp = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.GREEN);
		tp.setText("Hello World!");
		
		final Num h = bounds().height().mul(0.1);
		final Rect strbox = Rect.make(Num.ZERO, h).centerTo(bounds());
		
		tp.setRect(strbox);
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

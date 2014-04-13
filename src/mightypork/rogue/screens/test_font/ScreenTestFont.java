package mightypork.rogue.screens.test_font;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.constraints.RectBound;


public class ScreenTestFont extends Screen {
	
	private final TextPainter tp;
	
	
	public ScreenTestFont(AppAccess app) {
		super(app);
		
		tp = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.GREEN);
		tp.setText("Hello World!");
		
		final NumBound fontHeight = mul(getDisplay().getSize().yc(), 0.1);
		
		final RectBound strbox = centerTo(box(fontHeight), this);
		
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

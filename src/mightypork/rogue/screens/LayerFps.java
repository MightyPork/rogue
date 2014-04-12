package mightypork.rogue.screens;


import static mightypork.utils.math.constraints.Bounds.*;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.string.StringProvider;


public class LayerFps extends ScreenLayer {
	
	TextPainter tp;
	
	
	public LayerFps(Screen screen) {
		super(screen);
		
		/*
		 * Toggle key: F3
		 */
		bindKeyStroke(new KeyStroke(Keys.KEY_F3), new Action() {
			
			@Override
			public void execute()
			{
				setVisible(!isVisible());
			}
		});
		
		final GLFont font = Res.getFont("default");
		
		final RectBound constraint = box(add(topRight(this), -8, 8), 0, 32);
		
		tp = new TextPainter(font, Align.RIGHT, RGB.WHITE, new StringProvider() {
			
			@Override
			public String getString()
			{
				return getDisplay().getFps() + " fps";
			}
		});
		
		tp.setContext(constraint);
		
		tp.setShadow(RGB.BLACK, Vect.ONE);
	}
	
	
	@Override
	public void render()
	{
		tp.render();
	}
	
	
	@Override
	public int getPriority()
	{
		return Integer.MAX_VALUE;
	}
}

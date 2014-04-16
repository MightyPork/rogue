package mightypork.rogue.screens;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.control.Action;
import mightypork.util.math.color.Color;
import mightypork.util.string.StringProvider;


public class LayerFps extends ScreenLayer {
	
	TextPainter tp;
	
	
	public LayerFps(BaseScreen screen) {
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
		
		final RectBound constraint = root.topRight().add(-8, 8).expand(0, 0, 0, 32);
		
		tp = new TextPainter(font, AlignX.RIGHT, Color.WHITE, new StringProvider() {
			
			@Override
			public String getString()
			{
				return getDisplay().getFps() + " fps";
			}
		});
		
		tp.setRect(constraint);
		tp.setShadow(Color.BLACK, Vect.make(tp.height().div(16)));
		
		root.add(tp);
	}
	
	
	@Override
	public int getPriority()
	{
		return Integer.MAX_VALUE;
	}
}

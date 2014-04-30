package mightypork.rogue.screens;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;


public class FpsOverlay extends Overlay {
	
	TextPainter tp;
	
	
	public FpsOverlay(AppAccess screen)
	{
		super(screen);
		
		/*
		 * Toggle key: F3
		 */
		bindKey(new KeyStroke(Keys.F3), new Action() {
			
			@Override
			public void execute()
			{
				setVisible(!isVisible());
			}
		});
		
		final GLFont font = Res.getFont("default");
		
		final Num h = root.height();
		final RectBound constraint = root.shrink(h.perc(3)).topRight().startRect().growDown(h.perc(8).max(32));
		
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
	public int getZIndex()
	{
		return Integer.MAX_VALUE;
	}
}

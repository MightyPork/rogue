package mightypork.rogue.screens;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.strings.StringProvider;


/**
 * FPS indicator overlay
 * 
 * @author MightyPork
 */
public class FpsOverlay extends Overlay {
	
	TextPainter tp;
	
	
	public FpsOverlay(AppAccess screen)
	{
		super(screen);
		
		/*
		 * Toggle key: F3
		 */
		bindKey(new KeyStroke(Keys.F3), Edge.RISING, new Action() {
			
			@Override
			public void execute()
			{
				setVisible(!isVisible());
			}
		});
		
		final GLFont font = Res.getFont("thin");
		
		final Num h = root.height();
		final RectBound constraint = root.shrink(h.perc(3)).topRight().startRect().growDown(h.perc(5).max(16));
		
		tp = new TextPainter(font, AlignX.RIGHT, RGB.YELLOW, new StringProvider() {
			
			@Override
			public String getString()
			{
				return getDisplay().getFps() + " fps";
			}
		});
		
		tp.setRect(constraint);
		tp.setShadow(RGB.BLACK_60, Vect.make(tp.height().div(8).round()));
		
		root.add(tp);
		
		setVisible(false); // initially hide.
	}
	
	
	@Override
	public int getZIndex()
	{
		return Integer.MAX_VALUE;
	}
}

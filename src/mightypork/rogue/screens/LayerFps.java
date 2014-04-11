package mightypork.rogue.screens;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.string.StringProvider;


public class LayerFps extends ScreenLayer {
	
	TextPainter tp;
	
	
	public LayerFps(Screen screen) {
		super(screen);
		
		final StringProvider text = new StringProvider() {
			
			@Override
			public String getString()
			{
				return getDisplay().getFps() + " fps";
			}
		};
		
		final GLFont font = Res.getFont("default");
		
		final RectConstraint constraint = _box(_sub(_top_right(this), 8, 8), 0, 32);
		
		tp = new TextPainter(font, Align.RIGHT, RGB.WHITE, text);
		tp.setContext(constraint);
		
		tp.setShadow(RGB.BLACK, Vec.ONE);
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

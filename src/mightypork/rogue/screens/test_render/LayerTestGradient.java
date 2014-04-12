package mightypork.rogue.screens.test_render;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.gamecore.control.timing.Poller;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Vec;


public class LayerTestGradient extends ScreenLayer {
	
	private final Poller p = new Poller();
	
	private final RectConstraint pos1;
	private final RectConstraint pos2;
	
	
	public LayerTestGradient(Screen screen) {
		super(screen);
		
		pos1 = cCached(p, cGrowDown(cTopEdge(this), 64));
		pos2 = cCached(p, cShrinkTop(cGrowRight(cLeftEdge(this), 64), 64));
	}
	
	
	@Override
	public void render()
	{
		Render.quadColor(getRect(), RGB.WHITE, RGB.BLUE, RGB.BLACK, RGB.PURPLE);
		Render.quadGradH(pos1.getRect(), RGB.GREEN, RGB.RED);
		Render.quadGradV(pos2.getRect(), RGB.WHITE, RGB.PURPLE);
	}
	
	
	@Override
	public int getPriority()
	{
		return 5;
	}
	
	
	@Override
	protected void onSizeChanged(Vec size)
	{
		p.poll();
	}
	
}

package mightypork.rogue.screens.test_render;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.gamecore.control.timing.Poller;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.vect.Vect;


public class LayerTestGradient extends ScreenLayer {
	
	private final Poller p = new Poller();
	
	private final RectBound pos1;
	private final RectBound pos2;
	
	
	public LayerTestGradient(Screen screen) {
		super(screen);
		
		pos1 = cached(p, growDown(edgeTop(this), 64));
		pos2 = cached(p, shrinkTop(growRight(edgeLeft(this), 64), 64));
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
	protected void onSizeChanged(Vect size)
	{
		p.poll();
	}
	
}

package mightypork.rogue.screens.test_render;


import static mightypork.gamecore.gui.constraints.Constraints.*;
import mightypork.gamecore.gui.constraints.Poller;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class LayerTestGradient extends ScreenLayer {
	
	private final Poller p = new Poller();
	
	private final RectConstraint pos1;
	private final RectConstraint pos2;
	private final RectConstraint pos3;
	
	
	public LayerTestGradient(Screen screen) {
		super(screen);
		
		pos1 = _cache(p, _grow_down(_top_edge(this), 64));
		pos2 = _cache(p, _shrink_up(_grow_right(_left_edge(this), 64), 64));
		pos3 = _cache(p, _move(_grow(_center(this), 70, 10), 100, 100));
	}
	
	
	@Override
	public void render()
	{
		Render.quadGradH(pos1.getRect(), RGB.GREEN, RGB.RED);
		Render.quadGradV(pos2.getRect(), RGB.WHITE, RGB.PURPLE);
		Render.quad(pos3.getRect(), RGB.RED);
	}
	
	
	@Override
	public int getPriority()
	{
		return 5;
	}
	
	
	@Override
	protected void onSizeChanged(Coord size)
	{
		p.poll();
	}
	
}

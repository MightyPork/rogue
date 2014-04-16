package mightypork.rogue.screens.test_bouncyboxes;


import java.util.Random;

import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.Easing;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.mutable.NumAnimated;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.caching.RectCache;


public class BouncyBox extends VisualComponent implements Updateable {
	
	private final Random rand = new Random();
	
	private final RectCache box;
	
	private final NumAnimated pos = new NumAnimated(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		super();
		enableCaching(true);
		
		Rect abox;
		
		abox = leftEdge().growRight(height());
		abox = abox.move(width().sub(height()).mul(pos), Num.ZERO);
		abox = abox.shrink(height().perc(10));
		
		box = abox.cached();
	}
	
	
	@Override
	public void renderComponent()
	{
		Render.quad(box, Color.GREEN);
	}
	
	
	public void goLeft()
	{
		pos.animate(1, 0, 1 + rand.nextDouble() * 1);
	}
	
	
	public void goRight()
	{
		pos.animate(0, 1, 1 + rand.nextDouble() * 1);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (pos.isInProgress()) {
			pos.update(delta);
			box.poll();
		}
	}
	
	
	@Override
	public void updateLayout()
	{
		box.poll();
	}
	
}

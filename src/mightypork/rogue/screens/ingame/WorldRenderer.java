package mightypork.rogue.screens.ingame;


import mightypork.gamecore.control.events.MouseButtonEvent;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.render.Render;
import mightypork.rogue.world.World;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.control.timing.Updateable;
import mightypork.util.math.color.RGB;


public class WorldRenderer extends InputComponent implements Updateable {
	
	private final World world;
	private final Rect rightShadow;
	private final Rect leftShadow;
	private final Rect topShadow;
	private final Rect bottomShadow;
	
	
	public WorldRenderer(World world)
	{
		this.world = world;
		
		final Num h = height();
		final Num w = width();
		final Num minWH = w.min(h).max(700);
		
		final Num grX = w.perc(30);
		final Num grY = h.perc(20);
		
		leftShadow = leftEdge().growRight(grX);
		rightShadow = rightEdge().growLeft(grX);
		topShadow = topEdge().growDown(grY);
		bottomShadow = bottomEdge().growUp(grY); //.moveY(minWH.perc(-6))
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		System.out.println("world clciked, yo");
	}
	
	
	@Override
	protected void renderComponent()
	{
		world.render(this, 8, 7, 100);
		
		Render.quadGradH(leftShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradH(rightShadow, RGB.NONE, RGB.BLACK);
		
		Render.quadGradV(topShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradV(bottomShadow, RGB.NONE, RGB.BLACK);
	}
	
	
	@Override
	public void update(double delta)
	{
		world.update(delta);
	}
	
}

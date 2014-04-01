package mightypork.rogue.display.screens.screenBouncy;


import static mightypork.rogue.display.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.display.constraints.NumConstraint;
import mightypork.rogue.display.constraints.RectConstraint;
import mightypork.rogue.display.constraints.RenderContext;
import mightypork.rogue.display.constraints.Renderable;
import mightypork.rogue.textures.Render;
import mightypork.utils.control.timing.Updateable;
import mightypork.utils.control.timing.animation.AnimDouble;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Rect;
import mightypork.utils.math.easing.Easing;


public class BouncyBox implements Renderable, Updateable, RenderContext {
	
	private Random rand = new Random();
	
	private RenderContext context;
	
	private RectConstraint box;
	
	private AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		NumConstraint side = c_height(this);
		
		NumConstraint move_length = c_sub(c_width(this), side);
		
		NumConstraint offset = c_mul(move_length, c_n(pos));
		
		RectConstraint abox = c_sizedBox(this, offset, c_n(0), side, side);
		
		NumConstraint margin = c_percent(side, c_n(10));
		
		RectConstraint with_margin = c_shrink(abox, margin);
		
		box = with_margin;
	}
	
	
	@Override
	public Rect getRect()
	{
		return context.getRect();
	}
	
	
	@Override
	public void render()
	{
		Render.quadRect(box.getRect(), RGB.GREEN);
	}
	
	
	@Override
	public void setContext(RenderContext context)
	{
		this.context = context;
	}
	
	
	public void goLeft()
	{
		pos.animate(1, 0, 2 + rand.nextDouble() * 1);
	}
	
	
	public void goRight()
	{
		pos.animate(0, 1, 2 + rand.nextDouble() * 1);
	}
	
	
	@Override
	public void update(double delta)
	{
		pos.update(delta);
	}
	
}

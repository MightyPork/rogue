package mightypork.rogue.gui.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.gui.constraints.RenderableWithContext;
import mightypork.rogue.render.Render;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.constraints.NumConstraint;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


public class BouncyBox implements RenderableWithContext, Updateable, ConstraintContext {
	
	private Random rand = new Random();
	
	private ConstraintContext context;
	
	private RectConstraint box;
	
	private AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		// create box
		NumConstraint side = c_height(this);
		RectConstraint abox = c_box_sized(this, side, side);
		
		// move
		NumConstraint move_length = c_sub(c_width(this), side);
		NumConstraint offset = c_mul(move_length, c_n(pos));
		abox = c_move(abox, offset, c_n(0));
		
		// add padding
		abox = c_shrink(abox, c_percent(side, c_n(10)));
		
		box = abox;
	}
	
	
	@Override
	public Rect getRect()
	{
		return context.getRect();
	}
	
	
	@Override
	public void render()
	{
		Render.quad(box.getRect(), RGB.GREEN);
	}
	
	
	@Override
	public void setContext(ConstraintContext context)
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

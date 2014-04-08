package mightypork.rogue.gui.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.gui.renderers.PluggableRenderer;
import mightypork.rogue.render.Render;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.NumEvaluable;
import mightypork.utils.math.constraints.RectEvaluable;


public class BouncyBox extends PluggableRenderer implements Updateable {
	
	private final Random rand = new Random();
	
	private final RectEvaluable box;
	
	private final AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		// create box
		final NumEvaluable side = c_height(this);
		RectEvaluable abox = c_box(this, side, side);
		
		// move
		final NumEvaluable move_length = c_sub(c_width(this), side);
		final NumEvaluable offset = c_mul(move_length, c_n(pos));
		abox = c_move(abox, offset, c_n(0));
		
		// add padding
		abox = c_shrink(abox, c_percent(side, c_n(10)));
		
		box = abox;
	}
	
	
	@Override
	public void render()
	{
		Render.quad(box.getRect(), RGB.GREEN);
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
		pos.update(delta);
	}
	
}

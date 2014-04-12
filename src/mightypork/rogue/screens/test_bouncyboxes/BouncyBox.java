package mightypork.rogue.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.Bounds.*;

import java.util.Random;

import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.NumberBound;
import mightypork.utils.math.constraints.RectBound;


public class BouncyBox extends PluggableRenderer implements Updateable {
	
	private final Random rand = new Random();
	
	private final RectBound box;
	
	private final AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		// create box
		final NumberBound side = height(this);
		RectBound abox = box(this, side, side);
		
		// move
		final NumberBound move_length = sub(width(this), side);
		final NumberBound offset = mul(move_length, pos);
		abox = move(abox, offset, 0);
		
		// add padding
		abox = shrink(abox, perc(side, 10));
		
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

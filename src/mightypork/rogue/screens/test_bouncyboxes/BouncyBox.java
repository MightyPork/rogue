package mightypork.rogue.screens.test_bouncyboxes;


import java.util.Random;

import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.painters.AbstractPainter;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.constraints.RectBound;


public class BouncyBox extends AbstractPainter implements Updateable {
	
	private final Random rand = new Random();
	
	private final RectBound box;
	
	private final AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		// create box
		final NumBound side = height(this);
		RectBound abox = box(this, side, side);
		
		// move
		final NumBound move_length = sub(width(this), side);
		final NumBound offset = mul(move_length, pos);
		abox = move(abox, offset, 0);
		
		// add padding
		/*
		 *  leftEdge(this)
		 *  	.growRight(height(this))
		 *  	.move(
		 *  		width(this)
		 *  			.sub(height(this))
		 *  			.mul(pos),
		 *  		0)
		 *  	.shrink(
		 *  		height(this)
		 *  			.perc(10)
		 *  	)
		 */
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

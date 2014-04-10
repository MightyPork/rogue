package mightypork.rogue.screens.test_bouncyboxes;


import static mightypork.gamecore.gui.constraints.Constraints.*;

import java.util.Random;

import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.gui.constraints.NumberConstraint;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;


public class BouncyBox extends PluggableRenderer implements Updateable {
	
	private final Random rand = new Random();
	
	private final RectConstraint box;
	
	private final AnimDouble pos = new AnimDouble(0, Easing.BOUNCE_OUT);
	
	
	public BouncyBox() {
		// create box
		final NumberConstraint side = _height(this);
		RectConstraint abox = _box(this, side, side);
		
		// move
		final NumberConstraint move_length = _sub(_width(this), side);
		final NumberConstraint offset = _mul(move_length, pos);
		abox = _move(abox, offset, 0);
		
		// add padding
		abox = _shrink(abox, _percent(side, 10));
		
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

package mightypork.rogue.gui.screens.test_cat_sound;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.Res;
import mightypork.rogue.bus.events.MouseButtonEvent;
import mightypork.rogue.gui.Screen;
import mightypork.rogue.gui.ScreenLayer;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.render.Render;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final RectConstraint kittenbox;
	
	private final AnimDouble s = new AnimDouble(400, Easing.SINE_BOTH);
	private final AnimDouble x = new AnimDouble(200, Easing.ELASTIC_OUT);
	private final AnimDouble y = new AnimDouble(200, Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final Texture cat_tx = Res.getTexture("test.kitten");
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		kittenbox = c_move(c_box_sized(this, c_n(s), c_n(s)), c_n(x), c_n(y));
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				x.fadeTo(disp().getWidth() / 2 - s.getTo() / 2, 2);
				y.fadeTo(disp().getHeight() / 2 - s.getTo() / 2, 2);
			}
		});
	}
	
	
	@Override
	public void update(double delta)
	{
		s.update(delta);
		x.update(delta);
		y.update(delta);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		final Coord pos = event.getPos();
		
		final double newSize = 200 + rand.nextInt(600);
		
		final double t = 2;
		
		s.fadeTo(newSize, t / 2D);
		x.fadeTo(pos.x - newSize / 2D, t);
		y.fadeTo(pos.y - newSize / 2D, t);
	}
	
	
	@Override
	public void render()
	{
		Render.quadTextured(kittenbox.getRect(), cat_tx);
	}
	
}

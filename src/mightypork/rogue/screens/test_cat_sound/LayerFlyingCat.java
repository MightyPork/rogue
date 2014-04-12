package mightypork.rogue.screens.test_cat_sound;


import static mightypork.utils.math.constraints.Constraints.*;

import java.util.Random;

import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.AnimCoord;
import mightypork.utils.math.coord.FixedCoord;
import mightypork.utils.math.coord.Vec;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final AnimDouble size = new AnimDouble(400, Easing.SINE_BOTH);
	private final AnimCoord pos = new AnimCoord(Vec.ZERO, Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImagePainter cat;
	private final TextPainter tp;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		pos.setTo(getDisplay().getCenter());
		
		cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		
		cat.setContext(_align(_box(size, size), pos));
		
		tp = new TextPainter(Res.getFont("default"));
		tp.setAlign(Align.CENTER);
		tp.setColor(RGB.YELLOW);
		tp.setText("Meow!");
		tp.setShadow(RGB.dark(0.8), new FixedCoord(2, 2));
		tp.setContext(_align(_box(64, 64), _mouseX, _mouseY));
		
		/*
		 * Register keys
		 */
		bindKeyStroke(new KeyStroke(Keys.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				pos.animateWithSpeed(getDisplay().getCenter(), 300);
			}
		});
	}
	
	
	@Override
	public void update(double delta)
	{
		size.update(delta);
		pos.update(delta);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		final Vec pos = event.getPos();
		
		this.pos.animateWithSpeed(pos, 200);
		
		size.animate(200 + rand.nextInt(600), this.pos.getDuration() / 2);
	}
	
	
	@Override
	public void render()
	{
		cat.render();
		tp.render();
		
		//System.out.println(tp.getRect());
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
}

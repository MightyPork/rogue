package mightypork.rogue.gui.screens.test_cat_sound;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.Res;
import mightypork.rogue.bus.events.MouseButtonEvent;
import mightypork.rogue.gui.renderers.ImageRenderer;
import mightypork.rogue.gui.renderers.TextRenderer;
import mightypork.rogue.gui.renderers.TextRenderer.Align;
import mightypork.rogue.gui.screens.Screen;
import mightypork.rogue.gui.screens.ScreenLayer;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.input.Keys;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectEvaluable;
import mightypork.utils.math.coord.Coord;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final AnimDouble size = new AnimDouble(400, Easing.SINE_BOTH);
	private final AnimDouble xPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	private final AnimDouble yPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImageRenderer cat;
	private final TextRenderer text;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		xPos.setTo(disp().getWidth() / 2);
		yPos.setTo(disp().getHeight() / 2);
		
		cat = new ImageRenderer(Res.getTxQuad("test.kitten"));
		cat.setContext(c_centered(c_box(this, c_n(size), c_n(size)), c_n(xPos), c_n(yPos)));
		
		//@formatter:off
		final RectEvaluable flyingFontBox = c_centered(
				c_box(this, c_n(0), c_n(64)),
				input().c_mouse_x(),
				input().c_mouse_y()
		);
		//@formatter:on
		
		text = new TextRenderer(Res.getFont("default"), "YO", RGB.YELLOW, Align.CENTER);
		text.setContext(flyingFontBox);
		
		bindKeyStroke(new KeyStroke(Keys.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				xPos.fadeTo(disp().getWidth() / 2, 2);
				yPos.fadeTo(disp().getHeight() / 2, 2);
			}
		});
	}
	
	
	@Override
	public void update(double delta)
	{
		size.update(delta);
		xPos.update(delta);
		yPos.update(delta);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		final Coord pos = event.getPos();
		
		final double t = 2;
		
		size.fadeTo(100 + rand.nextInt(700), t / 2D);
		xPos.fadeTo(pos.x, t);
		yPos.fadeTo(pos.y, t);
	}
	
	
	@Override
	public void render()
	{
		cat.render();
		text.render();
	}
	
}

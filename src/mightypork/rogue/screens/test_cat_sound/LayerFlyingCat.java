package mightypork.rogue.screens.test_cat_sound;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.interf.Updateable;
import mightypork.gamecore.gui.renderers.ImageRenderer;
import mightypork.gamecore.gui.renderers.TextRenderer;
import mightypork.gamecore.gui.renderers.TextRenderer.Align;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.rogue.Res;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;
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
		
		xPos.setTo(DisplaySystem.getWidth() / 2);
		yPos.setTo(DisplaySystem.getHeight() / 2);
		
		cat = new ImageRenderer(Res.getTxQuad("test.kitten"));
		cat.setContext(c_centered(c_box(this, c_n(size), c_n(size)), c_n(xPos), c_n(yPos)));
		
		//@formatter:off
		final RectConstraint flyingFontBox = c_centered(
				c_box(this, c_n(0), c_n(64)),
				InputSystem.mouseX,
				InputSystem.mouseY
		);
		//@formatter:on
		
		text = new TextRenderer(Res.getFont("default"), RGB.YELLOW, Align.CENTER);
		text.setContext(flyingFontBox);
		
		bindKeyStroke(new KeyStroke(Keys.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				xPos.fadeTo(DisplaySystem.getWidth() / 2, 2);
				yPos.fadeTo(DisplaySystem.getHeight() / 2, 2);
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
		text.render(disp().getFps()+" fps");
	}
	
}

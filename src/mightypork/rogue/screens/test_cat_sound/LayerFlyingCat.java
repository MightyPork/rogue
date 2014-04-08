package mightypork.rogue.screens.test_cat_sound;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.interf.Updateable;
import mightypork.gamecore.gui.renderers.ImagePainter;
import mightypork.gamecore.gui.renderers.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.string.StringProvider;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final AnimDouble size = new AnimDouble(400, Easing.SINE_BOTH);
	private final AnimDouble xPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	private final AnimDouble yPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImagePainter cat;
	private final TextPainter text;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		xPos.setTo(DisplaySystem.getWidth() / 2);
		yPos.setTo(DisplaySystem.getHeight() / 2);
		
		cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		cat.setContext(c_centered(c_box(this, size, size), xPos, yPos));
		
		final RectConstraint fpsbox = c_centered(c_box(this, 0, 64), InputSystem.mouseX, InputSystem.mouseY);
		
		final StringProvider sp = new StringProvider() {
			
			@Override
			public String getString()
			{
				return getDisplay().getFps() + " fps";
			}
		};
		
		text = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.YELLOW, sp);
		
		text.setContext(fpsbox);
		
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
		text.render();
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
}

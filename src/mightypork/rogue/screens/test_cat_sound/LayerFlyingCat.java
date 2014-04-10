package mightypork.rogue.screens.test_cat_sound;


import static mightypork.gamecore.gui.constraints.Constraints.*;

import java.util.Random;

import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final AnimDouble size = new AnimDouble(400, Easing.SINE_BOTH);
	private final AnimDouble xPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	private final AnimDouble yPos = new AnimDouble(200, Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImagePainter cat;
	private final TextPainter tp;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		xPos.setTo(DisplaySystem.getWidth() / 2);
		yPos.setTo(DisplaySystem.getHeight() / 2);
		final RectConstraint catbox = _centered(_box(size, size), xPos, yPos);
		
		cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		cat.setContext(catbox);
		
		final RectConstraint fpsbox = _centered(_box(64, 64), _mouseX, _mouseY);
		
		tp = new TextPainter(Res.getFont("default"), Align.CENTER, RGB.YELLOW, "Meow");
		
		tp.setContext(fpsbox);
		
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
		tp.render();
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
}

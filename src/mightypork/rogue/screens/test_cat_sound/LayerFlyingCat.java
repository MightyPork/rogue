package mightypork.rogue.screens.test_cat_sound;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
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
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAnimated;
import mightypork.utils.math.vect.VectVal;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final AnimDouble size = new AnimDouble(400, Easing.SINE_BOTH);
	private final VectAnimated pos = VectAnimated.make(Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImagePainter cat;
	private final TextPainter tp;
	private final QuadPainter qp;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		pos.setTo(getDisplay().getCenter());
		pos.setDefaultDuration(3);
		
		cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		
		// Bounds.box(size,size).centerTo(pos)
		cat.setContext(centerTo(box(size, size), pos));
		
		tp = new TextPainter(Res.getFont("default"));
		tp.setAlign(Align.CENTER);
		tp.setColor(RGB.YELLOW);
		tp.setText("Meow!");
		tp.setShadow(RGB.dark(0.8), VectVal.make(2, 2));
		
		// Bounds.box(64,64).centerTo(cMousePos)
		tp.setContext(centerTo(box(64, 64), cMousePos));
		
		qp = QuadPainter.gradV(RGB.YELLOW, RGB.RED);
		
		// Bounds.wrap(cat).bottomLeft().expand(0,0,50,50)
		qp.setContext(expand(bottomLeft(cat), 0, 0, 50, 50));
		
		/*
		 * Register keys
		 */
		bindKeyStroke(new KeyStroke(Keys.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				pos.setTo(getDisplay().getCenter());
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
		
		final Vect pos = event.getPos();
		
		this.pos.setTo(pos);
		
		size.animate(200 + rand.nextInt(600), 1);
	}
	
	
	@Override
	public void render()
	{
		cat.render();
		tp.render();
		qp.render();
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
}

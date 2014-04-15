package mightypork.rogue.screens.test_cat_sound;


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
import mightypork.utils.math.Easing;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.NumAnimated;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectAnimated;


public class LayerFlyingCat extends ScreenLayer implements Updateable, MouseButtonEvent.Listener {
	
	private final NumAnimated size = new NumAnimated(400, Easing.SINE_BOTH);
	private final VectAnimated pos = VectAnimated.makeVar(Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	private final ImagePainter cat;
	private final TextPainter tp;
	private final QuadPainter qp;
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		pos.setTo(getDisplay().getCenter());
		pos.setDefaultDuration(3);
		
		cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		
		cat.setRect(Rect.make(size, size).centerTo(pos));
		
		tp = new TextPainter(Res.getFont("default"));
		tp.setAlign(Align.CENTER);
		tp.setColor(RGB.YELLOW);
		tp.setText("Meow!");
		tp.setShadow(RGB.dark(0.8), Vect.make(2, 2));
		
		tp.setRect(Rect.make(64, 64).centerTo(mouse()));
		
		qp = QuadPainter.gradV(RGB.YELLOW, RGB.RED);
		
		qp.setRect(cat.getRect().bottomLeft().expand(size.half(), Num.ZERO, Num.ZERO, size.half()));
		
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

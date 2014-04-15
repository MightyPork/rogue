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
import mightypork.utils.math.constraints.num.mutable.NumAnimated;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.mutable.VectAnimated;


public class LayerFlyingCat extends ScreenLayer implements MouseButtonEvent.Listener, Updateable {
	
	private final NumAnimated size = new NumAnimated(300, Easing.SINE_BOTH);
	private final VectAnimated cat_position = VectAnimated.makeVar(Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	
	public LayerFlyingCat(Screen screen) {
		super(screen);
		
		// timing
		updated.add(size);
		updated.add(cat_position);
		size.setTo(root.height().perc(60));
		
		// cat
		cat_position.setTo(getDisplay().getCenter());
		cat_position.setDefaultDuration(3);
		
		ImagePainter cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		cat.setRect(Rect.make(size, size).centerTo(cat_position));
		cat.enableCaching(false);
		
		// frame around cat
		QuadPainter cat_frame = QuadPainter.gradV(RGB.YELLOW, RGB.RED);
		cat_frame.setRect(cat.grow(cat.height().mul(0.05)));
		cat_frame.enableCaching(false);

		// frame shadow
		QuadPainter cat_shadow = new QuadPainter(RGB.dark(0.4));
		cat_shadow.setRect(cat_frame.move(Vect.make(cat.height().mul(0.05))));
		cat_shadow.enableCaching(false);

		root.add(cat_shadow);
		root.add(cat_frame);
		root.add(cat);
		
		
		// Meow
		TextPainter tp = new TextPainter(Res.getFont("press_start"));
		tp.setAlign(Align.CENTER);
		tp.setColor(RGB.YELLOW);
		tp.setText("Meow!");
		tp.setShadow(RGB.dark(0.5), Vect.make(tp.height().div(16)));
		tp.setRect(Rect.make(Num.ZERO, cat.height().half()).centerTo(mouse));
		tp.enableCaching(false);
		root.add(tp);
		/*
		 * Register keys
		 */
		bindKeyStroke(new KeyStroke(Keys.KEY_RETURN), new Runnable() {
			
			@Override
			public void run()
			{
				cat_position.setTo(getDisplay().getCenter());
			}
		});
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		this.cat_position.setTo(event.getPos());
		
		double newSize = root.height().perc(10 + rand.nextInt(40)).value();
		
		size.animate(newSize, 1);
	}
	
	
	@Override
	public int getPriority()
	{
		return 10;
	}
	
}

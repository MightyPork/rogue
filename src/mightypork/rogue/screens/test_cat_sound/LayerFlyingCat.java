package mightypork.rogue.screens.test_cat_sound;


import java.util.Random;

import mightypork.gamecore.control.events.MouseButtonEvent;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Res;
import mightypork.util.constraints.num.mutable.NumAnimated;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.mutable.VectAnimated;
import mightypork.util.math.Easing;
import mightypork.util.math.color.Color;


public class LayerFlyingCat extends ScreenLayer implements MouseButtonEvent.Listener {
	
	private final NumAnimated size = new NumAnimated(300, Easing.SINE_BOTH);
	private final VectAnimated cat_position = VectAnimated.makeVar(Easing.ELASTIC_OUT);
	
	private final Random rand = new Random();
	
	
	public LayerFlyingCat(BaseScreen screen) {
		super(screen);
		
		// timing
		updated.add(size);
		updated.add(cat_position);
		size.setTo(root.height().perc(60));
		
		// cat
		cat_position.setTo(getDisplay().getCenter());
		cat_position.setDefaultDuration(3);
		
		final ImagePainter cat = new ImagePainter(Res.getTxQuad("test.kitten"));
		cat.setRect(Rect.make(size).centerTo(cat_position));
		cat.enableCaching(false);
		
		// frame around cat
		final QuadPainter cat_frame = QuadPainter.gradV(Color.YELLOW, Color.RED);
		cat_frame.setRect(cat.grow(cat.height().mul(0.05)));
		cat_frame.enableCaching(false);
		
		// frame shadow
		final QuadPainter cat_shadow = new QuadPainter(Color.dark(0.4));
		cat_shadow.setRect(cat_frame.move(Vect.make(cat.height().mul(0.05))));
		cat_shadow.enableCaching(false);
		
		// add to root layout
		root.add(cat_shadow);
		root.add(cat_frame);
		root.add(cat);
		
		// Meow
		final TextPainter tp = new TextPainter(Res.getFont("press_start"));
		tp.setAlign(AlignX.CENTER);
		tp.setColor(Color.YELLOW);
		tp.setText("Meow!");
		tp.setShadow(Color.dark(0.5), Vect.make(tp.height().div(16)));
		tp.setRect(Rect.make(cat.height().half()).centerTo(mouse));
		tp.enableCaching(false);
		root.add(tp);
		
		/*
		 * Register keys
		 */
		bindKey(new KeyStroke(Keys.RETURN), new Runnable() {
			
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
		
		cat_position.setTo(event.getPos());
		
		final double newSize = root.height().perc(10 + rand.nextInt(40)).value();
		
		size.animate(newSize, 1);
	}
	
	
	@Override
	public int getPriority()
	{
		return 10;
	}
	
}

package mightypork.rogue.gui.screens.screenTextures;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.Random;

import mightypork.rogue.AppAccess;
import mightypork.rogue.Res;
import mightypork.rogue.bus.events.ActionRequest;
import mightypork.rogue.bus.events.MouseButtonEvent;
import mightypork.rogue.bus.events.RequestType;
import mightypork.rogue.gui.screens.Screen;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.render.Render;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.input.Keyboard;


public class ScreenTextureTest extends Screen implements MouseButtonEvent.Listener {
	
	private RectConstraint kittenbox;
	
	private AnimDouble s = new AnimDouble(400, Easing.SINE_BOTH);
	private AnimDouble x = new AnimDouble(200, Easing.ELASTIC_OUT);
	private AnimDouble y = new AnimDouble(200, Easing.ELASTIC_OUT);
	
	private Random rand = new Random();
	
	
	public ScreenTextureTest(AppAccess app) {
		super(app);
		
		kittenbox = c_move(c_box_sized(this, c_n(s), c_n(s)), c_n(x), c_n(y));
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				snd().fadeOutAllLoops();
				bus().schedule(new ActionRequest(RequestType.SHUTDOWN), 3);
			}
		});
	}
	
	
	@Override
	protected void deinitScreen()
	{
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		System.out.println("YOLO");
		Res.getLoop("test.wilderness").fadeIn();
	}
	
	
	@Override
	protected void onScreenLeave()
	{
	}
	
	
	@Override
	protected void renderScreen()
	{
		Render.quadTextured(kittenbox.getRect(), Res.getTexture("test.kitten"));
	}
	
	
	@Override
	protected void updateScreen(double delta)
	{
		s.update(delta);
		x.update(delta);
		y.update(delta);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		Coord pos = event.getPos();
		
		double newSize = 200 + rand.nextInt(600);
		
		double t = 2;
		
		s.fadeTo(newSize, t / 2D);
		x.fadeTo(pos.x - newSize / 2D, t);
		y.fadeTo(pos.y - newSize / 2D, t);
	}
	
}

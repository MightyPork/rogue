package mightypork.rogue.display;

import java.util.Random;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.events.MouseButtonEvent;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.util.RenderUtils;
import mightypork.utils.math.Polar;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.easing.Easing;
import mightypork.utils.time.animation.AnimDouble;
import mightypork.utils.time.animation.AnimDoubleDeg;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


public class ScreenTestAnimations extends Screen implements MouseButtonEvent.Listener {
	
	public ScreenTestAnimations(AppAccess app) {
		super(app);
	}
	
	private Random rand = new Random();
	
	private AnimDoubleDeg degAnim = new AnimDoubleDeg(0, Easing.ELASTIC_OUT);
	
	//@formatter:off
	private AnimDouble[] anims = new AnimDouble[] {
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			new AnimDouble(0, Easing.BOUNCE_OUT),
			
//			new AnimDouble(0, Easing.NONE),
//			new AnimDouble(0, Easing.LINEAR),
//			
//			new AnimDouble(0, Easing.QUADRATIC_IN),
//			new AnimDouble(0, Easing.QUADRATIC_OUT),
//			new AnimDouble(0, Easing.QUADRATIC_IN_OUT),
//			
//			new AnimDouble(0, Easing.CUBIC_IN),
//			new AnimDouble(0, Easing.CUBIC_OUT),
//			new AnimDouble(0, Easing.CUBIC_IN_OUT),
//			
//			new AnimDouble(0, Easing.QUADRATIC_IN),
//			new AnimDouble(0, Easing.QUADRATIC_OUT),
//			new AnimDouble(0, Easing.QUADRATIC_IN_OUT),
//			
//			new AnimDouble(0, Easing.QUINTIC_IN),
//			new AnimDouble(0, Easing.QUINTIC_OUT),
//			new AnimDouble(0, Easing.QUINTIC_IN_OUT),
//			
//			new AnimDouble(0, Easing.EXPO_IN),
//			new AnimDouble(0, Easing.EXPO_OUT),
//			new AnimDouble(0, Easing.EXPO_IN_OUT),
//			
//			new AnimDouble(0, Easing.SINE_IN),
//			new AnimDouble(0, Easing.SINE_OUT),
//			new AnimDouble(0, Easing.SINE_IN_OUT),
//			
//			new AnimDouble(0, Easing.CIRC_IN),
//			new AnimDouble(0, Easing.CIRC_OUT),
//			new AnimDouble(0, Easing.CIRC_IN_OUT),
//			
//			new AnimDouble(0, Easing.BOUNCE_IN),
//			new AnimDouble(0, Easing.BOUNCE_OUT),
//			new AnimDouble(0, Easing.BOUNCE_IN_OUT),
//			
//			new AnimDouble(0, Easing.BACK_IN),
//			new AnimDouble(0, Easing.BACK_OUT),
//			new AnimDouble(0, Easing.BACK_IN_OUT),
//			
//			new AnimDouble(0, Easing.ELASTIC_IN),
//			new AnimDouble(0, Easing.ELASTIC_OUT),
//			new AnimDouble(0, Easing.ELASTIC_IN_OUT),
	};
	//@formatter:on
	
	@Override
	public void initScreen()
	{
		bindKeyStroke(new KeyStroke(Keyboard.KEY_RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				for (AnimDouble a : anims) {
					a.animate(0, 1, 1 + rand.nextDouble() * 1);
				}
			}
		});
		
		bindKeyStroke(new KeyStroke(Keyboard.KEY_LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				for (AnimDouble a : anims) {
					a.animate(1, 0, 1 + rand.nextDouble() * 1);
				}
			}
		});
	}
	
	
	@Override
	protected void deinitScreen()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		// no impl
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		// no impl
	}
	
	
	@Override
	protected void updateScreen(double delta)
	{
		degAnim.update(delta);
		
		for (AnimDouble a : anims) {
			a.update(delta);
		}
	}
	
	
	@Override
	protected void renderScreen()
	{
		double screenH = Display.getHeight();
		double screenW = Display.getWidth();
		double perBoxH = screenH / anims.length;
		double padding = perBoxH * 0.1;
		double boxSide = perBoxH - padding * 2;
		
		for (int i = 0; i < anims.length; i++) {
			AnimDouble a = anims[i];
			
			RenderUtils.setColor(RGB.GREEN);
			RenderUtils.quadSize(padding + a.getCurrentValue() * (screenW - perBoxH), screenH - perBoxH * i - perBoxH + padding, boxSide, boxSide);
		}
		
		RenderUtils.setColor(RGB.YELLOW);
		RenderUtils.translate(new Coord(Display.getWidth() / 2, Display.getHeight() / 2));
		RenderUtils.rotateZ(degAnim.getCurrentValue());
		RenderUtils.quadSize(-10, -10, 20, 200);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (event.isDown()) {
			Coord vec = disp().getSize().half().vecTo(event.getPos());
			
			Polar p = Polar.fromCoord(vec);
			
			degAnim.fadeTo(p.getAngleDeg() - 90, 1.5);
		}
	}
	
}

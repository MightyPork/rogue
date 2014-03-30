package mightypork.rogue.display;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import mightypork.rogue.App;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.input.events.MouseButtonEvent;
import mightypork.rogue.input.events.MouseMotionEvent;
import mightypork.rogue.util.RenderUtils;
import mightypork.utils.math.Polar;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.easing.Easing;
import mightypork.utils.time.AnimDouble;
import mightypork.utils.time.AnimDoubleDeg;


public class ScreenSplash extends Screen {

	private AnimDoubleDeg degAnim = new AnimDoubleDeg(0, Easing.SINE);

	//@formatter:off
	private AnimDouble[] anims = new AnimDouble[] {
			new AnimDouble(0, Easing.NONE),
			new AnimDouble(0, Easing.LINEAR),
			
			new AnimDouble(0, Easing.QUADRATIC_IN),
			new AnimDouble(0, Easing.QUADRATIC_OUT),
			new AnimDouble(0, Easing.QUADRATIC),
			
			new AnimDouble(0, Easing.CUBIC_IN),
			new AnimDouble(0, Easing.CUBIC_OUT),
			new AnimDouble(0, Easing.CUBIC),
			
			new AnimDouble(0, Easing.QUADRATIC_IN),
			new AnimDouble(0, Easing.QUADRATIC_OUT),
			new AnimDouble(0, Easing.QUADRATIC),
			
			new AnimDouble(0, Easing.QUINTIC_IN),
			new AnimDouble(0, Easing.QUINTIC_OUT),
			new AnimDouble(0, Easing.QUINTIC_IN_OUT),
			
			new AnimDouble(0, Easing.EXPO_IN),
			new AnimDouble(0, Easing.EXPO_OUT),
			new AnimDouble(0, Easing.EXPO),
			
			new AnimDouble(0, Easing.SINE_IN),
			new AnimDouble(0, Easing.SINE_OUT),
			new AnimDouble(0, Easing.SINE),
			
			new AnimDouble(0, Easing.CIRC_IN),
			new AnimDouble(0, Easing.CIRC_OUT),
			new AnimDouble(0, Easing.CIRC),
	};
	//@formatter:on

	@Override
	public void initialize()
	{
		bindKeyStroke(new KeyStroke(Keyboard.KEY_RIGHT), new Runnable() {

			@Override
			public void run()
			{
				for (AnimDouble a : anims) {
					a.animate(0, 1, 3);
				}
			}
		});

		bindKeyStroke(new KeyStroke(Keyboard.KEY_LEFT), new Runnable() {

			@Override
			public void run()
			{
				for (AnimDouble a : anims) {
					a.animate(1, 0, 3);
				}
			}
		});
	}


	@Override
	protected void renderScreen()
	{
		double screenH = Display.getHeight();
		double screenW = Display.getWidth();
		double perBoxH = screenH / anims.length;
		double padding = perBoxH*0.1;
		double boxSide = perBoxH-padding*2;

		for (int i = 0; i < anims.length; i++) {
			AnimDouble a = anims[i];

			RenderUtils.setColor(i%3==0?RGB.GREEN:RGB.BLUE);
			RenderUtils.quadSize(
					padding + a.getCurrentValue() * (screenW - perBoxH - padding*2),
					screenH - perBoxH * i - perBoxH + padding,
					boxSide,
					boxSide
			);
		}

		RenderUtils.setColor(RGB.YELLOW);
		RenderUtils.translate(new Coord(Display.getWidth() / 2, Display.getHeight() / 2));
		RenderUtils.rotateZ(degAnim.getCurrentValue());
		RenderUtils.quadSize(-10, -10, 20, 200);
	}


	@Override
	public void receive(MouseMotionEvent event)
	{
	}


	@Override
	public void receive(MouseButtonEvent event)
	{
		if(event.isDown()) {
			Coord vec = App.disp().getSize().half().vecTo(event.getPos());

			Polar p = Polar.fromCoord(vec);

			degAnim.fadeTo(p.getAngleDeg() - 90, 0.2);
		}
	}


	@Override
	protected void onEnter()
	{
		// TODO Auto-generated method stub

	}


	@Override
	protected void onLeave()
	{
		// TODO Auto-generated method stub

	}


	@Override
	protected void onSizeChanged(Coord size)
	{
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateScreen(double delta)
	{
		degAnim.update(delta);

		for (AnimDouble a : anims) {
			a.update(delta);
		}
	}

}

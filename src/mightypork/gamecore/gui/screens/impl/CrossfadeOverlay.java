package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.core.events.ShudownRequest;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.timing.TimedTask;


public class CrossfadeOverlay extends Overlay {
	
	private static final double T_IN = 0.4;
	private static final double T_OUT = 0.6;
	
	NumAnimated alpha = new NumAnimated(0);
	String requestedScreenName;
	
	TimedTask revealTask = new TimedTask() {
		
		@Override
		public void run()
		{
			if (requestedScreenName == null) {
				getEventBus().send(new ShudownRequest());
			} else {
				getEventBus().send(new ScreenRequest(requestedScreenName));
			}
			alpha.setEasing(Easing.SINE_OUT);
			alpha.fadeOut(T_OUT);
		}
	};
	
	
	public CrossfadeOverlay(AppAccess app)
	{
		super(app);
		
		final QuadPainter qp = new QuadPainter(RGB.BLACK); // TODO allow custom colors
		qp.setRect(root);
		root.add(qp);
		
		updated.add(alpha);
		updated.add(revealTask);
		
		setAlpha(alpha);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 10000; // not too high, so app can put something on top
	}
	
	
	public void goToScreen(String screen, boolean fromDark)
	{
		requestedScreenName = screen;
		
		if (fromDark) {
			alpha.setTo(1);
			revealTask.run();
		} else {
			revealTask.start(T_IN);
			
			alpha.setEasing(Easing.SINE_IN);
			alpha.fadeIn(T_IN);
			
		}
	}
	
}

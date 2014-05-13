package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.timing.TimedTask;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


public class CrossfadeOverlay extends Overlay {
	
	private static final double T_IN = 0.5;
	private static final double T_OUT = 0.7;
	
	NumAnimated blackLevel = new NumAnimated(0);
	
	Color color = Color.dark(blackLevel);
	String requestedScreenName;
	
	TimedTask revealTask = new TimedTask() {
		
		@Override
		public void run()
		{
			if (requestedScreenName == null) {
				getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
			} else {
				getEventBus().send(new ScreenRequest(requestedScreenName));
			}
			blackLevel.setEasing(Easing.SINE_OUT);
			blackLevel.fadeOut(T_OUT);
		}
	};
	
	
	public CrossfadeOverlay(AppAccess app)
	{
		super(app);
		
		final QuadPainter qp = new QuadPainter(color);
		qp.setRect(root);
		root.add(qp);
		
		updated.add(blackLevel);
		updated.add(revealTask);
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
			blackLevel.setTo(1);
			revealTask.run();
		} else {
			revealTask.start(T_IN);
			
			blackLevel.setEasing(Easing.SINE_IN);
			blackLevel.fadeIn(T_IN);
			
		}
	}
	
}

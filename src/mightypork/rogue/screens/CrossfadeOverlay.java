package mightypork.rogue.screens;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.events.ScreenRequestEvent;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.util.constraints.num.mutable.NumAnimated;
import mightypork.util.control.timing.TimedTask;
import mightypork.util.math.Easing;
import mightypork.util.math.color.Color;


public class CrossfadeOverlay extends Overlay implements CrossfadeRequest.Listener {
	
	private static final double T_IN = 0.5;
	private static final double T_OUT = 0.7;
	
	NumAnimated level = new NumAnimated(0);
	
	Color color = Color.dark(level);
	String requestedScreenName;
	
	TimedTask tt = new TimedTask() {
		
		@Override
		public void run()
		{
			if(requestedScreenName == null) shutdown();
			getEventBus().send(new ScreenRequestEvent(requestedScreenName));
		}
	};
	
	TimedTask tt2 = new TimedTask() {
		
		@Override
		public void run()
		{
			level.setEasing(Easing.SINE_OUT);
			level.fadeOut(T_OUT);
		}
	};
	
	
	public CrossfadeOverlay(AppAccess app) {
		super(app);
		
		QuadPainter qp = new QuadPainter(color);
		qp.setRect(root);
		root.add(qp);
		
		updated.add(level);
		updated.add(tt);
		updated.add(tt2);
	}
	
	
	@Override
	public int getPriority()
	{
		return Integer.MAX_VALUE - 1; // let FPS go on top
	}
	
	
	@Override
	public void goToScreen(String screen)
	{
		tt.start(T_IN);
		tt2.start(T_IN);
		
		level.setEasing(Easing.SINE_IN);
		level.fadeIn(T_IN);
		
		requestedScreenName = screen;
	}
	
}

package mightypork.gamecore.gui.screens;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequestEvent;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.timing.TimedTask;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


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
			if (requestedScreenName == null) {
				getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
			} else {
				getEventBus().send(new ScreenRequestEvent(requestedScreenName));
			}
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
	
	
	public CrossfadeOverlay(AppAccess app)
	{
		super(app);
		
		final QuadPainter qp = new QuadPainter(color);
		qp.setRect(root);
		root.add(qp);
		
		updated.add(level);
		updated.add(tt);
		updated.add(tt2);
	}
	
	
	@Override
	public int getZIndex()
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

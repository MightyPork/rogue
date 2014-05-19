package mightypork.gamecore.render.events;


import mightypork.gamecore.eventbus.BusEvent;


public class ScreenshotRequest extends BusEvent<ScreenshotRequestListener> {
	
	@Override
	protected void handleBy(ScreenshotRequestListener handler)
	{
		handler.onScreenshotRequest();
	}
	
}

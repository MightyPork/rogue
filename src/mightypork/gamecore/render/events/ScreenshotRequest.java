package mightypork.gamecore.render.events;


import mightypork.utils.eventbus.BusEvent;


public class ScreenshotRequest extends BusEvent<ScreenshotRequestListener> {
	
	@Override
	protected void handleBy(ScreenshotRequestListener handler)
	{
		handler.onScreenshotRequest();
	}
	
}

package mightypork.gamecore.render.events;


import mightypork.utils.eventbus.BusEvent;


public class DisplayReadyEvent extends BusEvent<DisplayReadyListener> {
	
	@Override
	protected void handleBy(DisplayReadyListener handler)
	{
		handler.onDisplayReady();
	}
	
}

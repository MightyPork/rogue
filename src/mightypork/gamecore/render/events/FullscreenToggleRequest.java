package mightypork.gamecore.render.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;
import mightypork.gamecore.render.DisplaySystem;


@SingleReceiverEvent
public class FullscreenToggleRequest extends BusEvent<DisplaySystem> {
	
	@Override
	protected void handleBy(DisplaySystem handler)
	{
		handler.switchFullscreen();
	}
}

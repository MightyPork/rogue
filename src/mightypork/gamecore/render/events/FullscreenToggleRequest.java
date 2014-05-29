package mightypork.gamecore.render.events;


import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


@SingleReceiverEvent
public class FullscreenToggleRequest extends BusEvent<DisplaySystem> {
	
	@Override
	protected void handleBy(DisplaySystem handler)
	{
		handler.switchFullscreen();
	}
}

package mightypork.gamecore.render.events;


import mightypork.gamecore.render.GraphicsModule;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


@SingleReceiverEvent
public class FullscreenToggleRequest extends BusEvent<GraphicsModule> {
	
	@Override
	protected void handleBy(GraphicsModule handler)
	{
		handler.switchFullscreen();
	}
}

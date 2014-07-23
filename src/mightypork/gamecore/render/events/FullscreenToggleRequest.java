package mightypork.gamecore.render.events;


import mightypork.gamecore.render.RenderModule;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


@SingleReceiverEvent
public class FullscreenToggleRequest extends BusEvent<RenderModule> {
	
	@Override
	protected void handleBy(RenderModule handler)
	{
		handler.switchFullscreen();
	}
}

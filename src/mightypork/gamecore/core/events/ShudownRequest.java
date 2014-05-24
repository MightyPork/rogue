package mightypork.gamecore.core.events;


import mightypork.gamecore.core.modules.MainLoop;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;


/**
 * Shutdown request, non-interactive. Shutdown needs to execute on GL thread for
 * display to deinit properly.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
@NonConsumableEvent
public class ShudownRequest extends BusEvent<MainLoop> {
	
	@Override
	public void handleBy(final MainLoop handler)
	{
		handler.queueTask(new Runnable() {
			
			@Override
			public void run()
			{
				handler.shutdown();
			}
		}, true);
	}
}

package mightypork.gamecore.core;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;


/**
 * Shutdown request. Shutdown needs to execute on GL thread for display to
 * deinit properly.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
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
		});
	}
}

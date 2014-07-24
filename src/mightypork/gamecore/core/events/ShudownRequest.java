package mightypork.gamecore.core.events;


import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.core.modules.MainLoop;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.NonConsumableEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


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
				App.shutdown();
			}
		}, true);
	}
}

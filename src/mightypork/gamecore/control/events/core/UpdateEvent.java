package mightypork.gamecore.control.events.core;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.event_flags.ImmediateEvent;
import mightypork.util.control.eventbus.event_flags.NonConsumableEvent;
import mightypork.util.control.eventbus.event_flags.UnloggedEvent;
import mightypork.util.timing.Updateable;


/**
 * Delta timing update event. Not logged.
 * 
 * @author MightyPork
 */
@UnloggedEvent
@ImmediateEvent
@NonConsumableEvent
public class UpdateEvent extends BusEvent<Updateable> {
	
	private final double deltaTime;
	
	
	/**
	 * @param deltaTime time since last update (sec)
	 */
	public UpdateEvent(double deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	
	@Override
	public void handleBy(Updateable handler)
	{
		handler.update(deltaTime);
	}
}

package mightypork.gamecore.eventbus.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.ImmediateEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;
import mightypork.gamecore.eventbus.event_flags.UnloggedEvent;


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

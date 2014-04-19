package mightypork.gamecore.control.events;


import mightypork.util.control.eventbus.events.Event;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;
import mightypork.util.control.eventbus.events.flags.UnloggedEvent;
import mightypork.util.control.timing.Updateable;


/**
 * Delta timing update event. Not logged.
 * 
 * @author MightyPork
 */
@UnloggedEvent
@ImmediateEvent
public class UpdateEvent implements Event<Updateable> {
	
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

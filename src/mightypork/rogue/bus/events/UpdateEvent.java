package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.ImmediateEvent;
import mightypork.utils.control.bus.events.types.UnloggedEvent;
import mightypork.utils.control.interf.Updateable;


/**
 * Delta timing update event
 * 
 * @author MightyPork
 */
// sending via queue would hog the bus
@ImmediateEvent
@UnloggedEvent
public class UpdateEvent implements Event<Updateable> {
	
	private final double deltaTime;
	
	
	/**
	 * @param deltaTime time since last update (sec)
	 */
	public UpdateEvent(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	
	@Override
	public void handleBy(Updateable handler)
	{
		handler.update(deltaTime);
	}
}

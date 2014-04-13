package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.ImmediateEvent;
import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.gamecore.control.timing.Updateable;


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
	public UpdateEvent(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	
	@Override
	public void handleBy(Updateable handler)
	{
		handler.update(deltaTime);
	}
}

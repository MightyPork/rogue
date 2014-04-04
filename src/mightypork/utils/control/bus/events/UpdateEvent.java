package mightypork.utils.control.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.interf.Updateable;


/**
 * Delta timing update event
 * 
 * @author MightyPork
 */
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

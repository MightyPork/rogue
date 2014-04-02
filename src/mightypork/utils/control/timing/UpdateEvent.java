package mightypork.utils.control.timing;


import mightypork.utils.control.bus.Handleable;


/**
 * Delta timing update event
 * 
 * @author MightyPork
 */
public class UpdateEvent implements Handleable<Updateable> {
	
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

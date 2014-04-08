package mightypork.gamecore.control.bus;




/**
 * Access to an {@link EventBus} instance
 * 
 * @author MightyPork
 */
public interface BusAccess {
	
	/**
	 * @return event bus
	 */
	EventBus getEventBus();
	
}

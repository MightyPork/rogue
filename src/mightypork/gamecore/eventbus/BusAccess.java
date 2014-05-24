package mightypork.gamecore.eventbus;


/**
 * Access to an {@link EventBus} instance
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface BusAccess {
	
	/**
	 * @return event bus
	 */
	EventBus getEventBus();
	
}

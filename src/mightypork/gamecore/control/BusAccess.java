package mightypork.gamecore.control;


import mightypork.gamecore.control.bus.EventBus;


public interface BusAccess {
	
	/**
	 * @return event bus
	 */
	EventBus bus();
	
}

package mightypork.gamecore;


import mightypork.gamecore.control.bus.EventBus;


public interface BusAccess {
	
	/**
	 * @return event bus
	 */
	public abstract EventBus bus();
	
}

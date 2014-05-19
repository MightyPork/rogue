package mightypork.gamecore.input.events;


import mightypork.gamecore.eventbus.BusEvent;


public class InputReadyEvent extends BusEvent<InputReadyListener> {
	
	@Override
	protected void handleBy(InputReadyListener handler)
	{
		handler.onInputReady();
	}
	
}

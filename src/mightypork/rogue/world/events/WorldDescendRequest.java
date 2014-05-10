package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;


public class WorldDescendRequest extends BusEvent<WorldDescendRequestListener> {
	
	@Override
	protected void handleBy(WorldDescendRequestListener handler)
	{
		handler.onDescendRequest();
	}
	
}

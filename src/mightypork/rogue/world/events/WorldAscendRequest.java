package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;


/**
 * Player wants to go up
 * 
 * @author MightyPork
 */
public class WorldAscendRequest extends BusEvent<WorldAscendRequestListener> {
	
	@Override
	protected void handleBy(WorldAscendRequestListener handler)
	{
		handler.onAscendRequest();
	}
	
}

package mightypork.rogue.world.events;


import mightypork.utils.eventbus.BusEvent;


/**
 * Player wants to go up
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class WorldAscendRequest extends BusEvent<WorldAscendRequestListener> {
	
	@Override
	protected void handleBy(WorldAscendRequestListener handler)
	{
		handler.onAscendRequest();
	}
	
}

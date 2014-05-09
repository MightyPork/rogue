package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;


public class PlayerKilledEvent extends BusEvent<PlayerKilledListener> {
	
	@Override
	protected void handleBy(PlayerKilledListener handler)
	{
		handler.onPlayerKilled();
	}
}

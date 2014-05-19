package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;


public class PlayerKilledEvent extends BusEvent<PlayerDeathHandler> {
	
	@Override
	protected void handleBy(PlayerDeathHandler handler)
	{
		handler.onPlayerKilled();
	}
}

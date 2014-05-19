package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.world.WorldProvider;


public class PlayerKilledEvent extends BusEvent<PlayerDeathHandler> {
	
	@Override
	protected void handleBy(PlayerDeathHandler handler)
	{
		// not dead, discard event.
		if (!WorldProvider.get().getPlayer().isDead()) return;
		
		handler.onPlayerKilled();
	}
}

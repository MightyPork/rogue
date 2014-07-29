package mightypork.rogue.world.events;


import mightypork.rogue.world.WorldProvider;
import mightypork.utils.eventbus.BusEvent;


public class PlayerKilledEvent extends BusEvent<PlayerDeathHandler> {
	
	@Override
	protected void handleBy(PlayerDeathHandler handler)
	{
		// not dead, discard event.
		if (!WorldProvider.get().getPlayer().isDead()) return;
		
		handler.onPlayerKilled();
	}
}

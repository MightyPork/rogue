package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.world.World;


/**
 * Toggle world pause state
 * 
 * @author MightyPork
 */
public class WorldPauseRequest extends BusEvent<World> {
	
	
	@Override
	protected void handleBy(World handler)
	{
		// toggle paused state
		if (!handler.isPaused()) {
			handler.pause();
		} else {
			handler.resume();
		}
	}
	
}

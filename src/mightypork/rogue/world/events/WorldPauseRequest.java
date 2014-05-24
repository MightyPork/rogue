package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.world.World;


/**
 * Toggle world pause state
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class WorldPauseRequest extends BusEvent<World> {
	
	public static enum PauseAction
	{
		PAUSE, RESUME, TOGGLE;
	}
	
	private final PauseAction op;
	
	
	public WorldPauseRequest(PauseAction op)
	{
		super();
		this.op = op;
	}
	
	
	@Override
	protected void handleBy(World handler)
	{
		if (op == PauseAction.PAUSE) {
			handler.pause();
			return;
		}
		
		if (op == PauseAction.RESUME) {
			handler.resume();
			return;
		}
		
		// else
		
		// toggle paused state
		if (!handler.isPaused()) {
			handler.pause();
		} else {
			handler.resume();
		}
	}
	
}

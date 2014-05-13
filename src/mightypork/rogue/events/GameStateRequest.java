package mightypork.rogue.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.GameStateManager;
import mightypork.rogue.GameStateManager.GameState;


/**
 * Request for a game state change
 * 
 * @author MightyPork
 */
public class GameStateRequest extends BusEvent<GameStateManager> {
	
	final private GameState requested;
	
	
	public GameStateRequest(GameState requested)
	{
		this.requested = requested;
	}
	
	
	@Override
	protected void handleBy(GameStateManager handler)
	{
		handler.triggerAction(requested);
	}
}

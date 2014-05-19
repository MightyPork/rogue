package mightypork.rogue.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.RogueStateManager;
import mightypork.rogue.RogueStateManager.RogueState;


/**
 * Request for a game state change
 * 
 * @author MightyPork
 */
public class GameStateRequest extends BusEvent<RogueStateManager> {
	
	final private RogueState requested;
	
	
	public GameStateRequest(RogueState requested)
	{
		this.requested = requested;
	}
	
	
	@Override
	protected void handleBy(RogueStateManager handler)
	{
		handler.triggerAction(requested);
	}
}

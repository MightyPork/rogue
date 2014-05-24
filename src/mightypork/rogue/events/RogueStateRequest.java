package mightypork.rogue.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.RogueStateManager;
import mightypork.rogue.RogueStateManager.RogueState;


/**
 * Request for a game state change
 * 
 * @author Ondřej Hruška
 */
public class RogueStateRequest extends BusEvent<RogueStateManager> {
	
	final private RogueState requested;
	private final boolean fromDark;
	
	
	public RogueStateRequest(RogueState requested)
	{
		this.requested = requested;
		this.fromDark = false;
	}
	
	
	public RogueStateRequest(RogueState requested, boolean fromDark)
	{
		this.requested = requested;
		this.fromDark = fromDark;
	}
	
	
	@Override
	protected void handleBy(RogueStateManager handler)
	{
		handler.triggerAction(requested, fromDark);
	}
}

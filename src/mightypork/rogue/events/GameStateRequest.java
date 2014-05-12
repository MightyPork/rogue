package mightypork.rogue.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.rogue.GameStateManager;
import mightypork.rogue.GameStateManager.GameState;


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

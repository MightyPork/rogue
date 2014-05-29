package mightypork.rogue.world.events;


import mightypork.utils.eventbus.BusEvent;


public class GameWinEvent extends BusEvent<GameWinHandler> {
	
	@Override
	protected void handleBy(GameWinHandler handler)
	{
		handler.onGameWon();
	}
}

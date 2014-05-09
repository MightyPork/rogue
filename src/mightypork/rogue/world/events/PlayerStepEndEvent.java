package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.UnloggedEvent;
import mightypork.rogue.world.entity.entities.PlayerEntity;


@UnloggedEvent
public class PlayerStepEndEvent extends BusEvent<PlayerStepEndListener> {
	
	private final PlayerEntity player;
	
	
	public PlayerStepEndEvent(PlayerEntity player)
	{
		super();
		this.player = player;
	}
	
	
	@Override
	protected void handleBy(PlayerStepEndListener handler)
	{
		handler.onStepFinished(player);
	}
	
}

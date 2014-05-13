package mightypork.rogue.world.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NotLoggedEvent;
import mightypork.rogue.world.entity.impl.PlayerEntity;


@NotLoggedEvent
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

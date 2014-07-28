package mightypork.rogue.world.events;


import mightypork.rogue.world.entity.impl.EntityPlayer;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.NotLoggedEvent;


@NotLoggedEvent
public class PlayerStepEndEvent extends BusEvent<PlayerStepEndListener> {

	private final EntityPlayer player;


	public PlayerStepEndEvent(EntityPlayer player)
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

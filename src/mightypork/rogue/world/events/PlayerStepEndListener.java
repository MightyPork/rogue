package mightypork.rogue.world.events;


import mightypork.rogue.world.entity.impl.PlayerEntity;


public interface PlayerStepEndListener {
	
	void onStepFinished(PlayerEntity player);
}

package mightypork.rogue.world.events;


import mightypork.rogue.world.entity.entities.PlayerEntity;


public interface PlayerStepEndListener {
	
	void onStepFinished(PlayerEntity player);
}

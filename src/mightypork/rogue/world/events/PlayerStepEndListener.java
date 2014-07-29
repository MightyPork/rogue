package mightypork.rogue.world.events;


import mightypork.rogue.world.entity.impl.EntityPlayer;


public interface PlayerStepEndListener {
	
	void onStepFinished(EntityPlayer player);
}

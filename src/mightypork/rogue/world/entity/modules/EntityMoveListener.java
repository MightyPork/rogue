package mightypork.rogue.world.entity.modules;

import mightypork.rogue.world.entity.Entity;




public interface EntityMoveListener {
	
	/**
	 * One step of a path finished
	 */
	void onStepFinished(Entity entity);
	
	
	/**
	 * Scheduled path finished
	 */
	void onPathFinished(Entity entity);
	
	
	/**
	 * Path was interrupted (bumped into a wall or entity)
	 */
	void onPathInterrupted(Entity entity);
	
}

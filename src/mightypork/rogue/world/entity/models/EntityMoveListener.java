package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;


public interface EntityMoveListener {
	
	/**
	 * One step of a path finished
	 */
	void onStepFinished(Entity entity, World world, Level level);
	
	
	/**
	 * Scheduled path finished
	 */
	void onPathFinished(Entity entity, World world, Level level);
	
	
	/**
	 * Path was interrupted (bumped into a wall or entity)
	 */
	void onPathInterrupted(Entity entity, World world, Level level);
	
}

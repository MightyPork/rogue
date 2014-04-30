package mightypork.rogue.world.entity.modules;




public interface EntityMoveListener {
	
	/**
	 * One step of a path finished
	 */
	void onStepFinished();
	
	
	/**
	 * Scheduled path finished
	 */
	void onPathFinished();
	
	
	/**
	 * Path was interrupted (bumped into a wall or entity)
	 */
	void onPathInterrupted();
	
}

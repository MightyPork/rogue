package mightypork.rogue.world;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


public abstract class WorldEntity implements IonBundled {
	
	private final WorldPos position = new WorldPos();
	private Runnable moveEndListener, pathEndListener;
	
	private long serial_id = 0L;
	
	private final Queue<PathStep> path = new LinkedList<>();
	
	
	public WorldEntity(ServerWorld world, WorldPos pos) {
		this.serial_id = world.genEid();
		this.position.setTo(pos);
	}
	
	
	public WorldEntity() {
		// for ion
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.putBundled("pos", position);
		bundle.putSequence("steps", path);
		bundle.put("eid", serial_id);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		bundle.loadBundled("pos", position);
		bundle.loadSequence("path", path);
		serial_id = bundle.get("eid", 0L);
	}
	
	
	public void setMoveEndListener(Runnable moveEndListener)
	{
		this.moveEndListener = moveEndListener;
	}
	
	
	public void setPathEndListener(Runnable pathEndListener)
	{
		this.pathEndListener = pathEndListener;
	}
	
	
	public WorldPos getPosition()
	{
		return position;
	}
	
	
	public void setPosition(WorldPos pos)
	{
		position.setTo(pos);
	}
	
	
	public void setPosition(int x, int y, int floor)
	{
		position.setTo(x, y, floor);
		cancelPath(); // discard remaining steps
	}
	
	
	/**
	 * @param world the world
	 * @param delta delta time
	 */
	public void updateLogic(WorldAccess world, double delta)
	{
		if(!isPhysical()) return;
		
		if (!position.isFinished()) {
			position.update(delta);
		}
		
		if (position.isFinished()) {
			
			if (moveEndListener != null) moveEndListener.run();
			
			if (!path.isEmpty()) {
				// get next step to walk
				PathStep step = path.poll();
				position.walk(step.x, step.y, getStepTime());
			} else {
				// notify AI or whatever
				if (pathEndListener != null) pathEndListener.run();
			}
		}
	}
	
	
	/**
	 * @param world the world
	 * @param delta delta time
	 */
	public void updateVisual(WorldAccess world, double delta)
	{
	}
	
	
	public boolean isPathFinished()
	{
		return position.isFinished() && path.isEmpty();
	}
	
	
	public void addStep(PathStep step)
	{
		path.add(step);
	}
	
	
	public void cancelPath()
	{
		path.clear();
	}
	
	
	protected abstract double getStepTime();
	
	public boolean isPhysical() {
		return true;
	}
	
}

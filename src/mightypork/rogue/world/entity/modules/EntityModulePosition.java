package mightypork.rogue.world.entity.modules;


import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.PathStep;
import mightypork.rogue.world.pathfinding.PathFinder;
import mightypork.rogue.world.pathfinding.PathFindingContext;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.math.constraints.vect.VectConst;


public class EntityModulePosition implements EntityModule {
	
	private final Entity entity;
	
	/** Last pos, will be freed upon finishing move */
	private final Coord lastPos = new Coord(0, 0);
	private boolean walking = false;
	
	private final Queue<PathStep> path = new LinkedList<>();
	private final EntityPos entityPos = new EntityPos();
	private double stepTime = 0.5;
	
	// marks for simple renderers
	public int lastXDir = 1;
	public int lastYDir = 1;
	
	private final Set<EntityMoveListener> moveListeners = new LinkedHashSet<>();
	
	
	public EntityModulePosition(Entity entity) {
		this.entity = entity;
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.putSequence("path", path);
		bundle.putBundled("pos", entityPos);
		bundle.put("step_time", stepTime);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		bundle.loadSequence("path", path);
		bundle.loadBundled("pos", entityPos);
		
		stepTime = bundle.get("step_time", stepTime);
	}
	
	
	public void setPosition(Coord coord)
	{
		entityPos.setTo(coord);
		lastPos.setTo(coord);
		cancelPath(); // discard remaining steps
	}
	
	
	/**
	 * @param delta delta time
	 */
	@Override
	public void update(double delta)
	{
		if (!entityPos.isFinished()) {
			entityPos.update(delta);
		}
		
		if (walking && entityPos.isFinished()) {
			walking = false;
			entity.getLevel().freeTile(lastPos);
			
			for (final EntityMoveListener l : moveListeners) {
				l.onStepFinished(entity);
			}
			
			if (path.isEmpty()) {
				for (final EntityMoveListener l : moveListeners) {
					l.onPathFinished(entity);
				}
			}
		}
		
		if (!walking && !path.isEmpty()) {
			
			walking = true;
			
			final PathStep step = path.poll();
			
			final Coord planned = entityPos.getCoord().add(step.toCoord());
			
			if (!entity.getLevel().isWalkable(planned)) {
				cancelPath();
				
				for (final EntityMoveListener l : moveListeners) {
					l.onPathInterrupted(entity);
				}
				
				walking = false;
			} else {
				
				// tmp for renderer
				if (step.x != 0) this.lastXDir = step.x;
				if (step.y != 0) this.lastYDir = step.y;
				
				lastPos.setTo(entityPos.getCoord());
				entityPos.walk(step, stepTime);
				entity.getLevel().occupyTile(planned);
			}
		}
	}
	
	
	public boolean isPathFinished()
	{
		return entityPos.isFinished() && path.isEmpty();
	}
	
	
	public void addStep(PathStep step)
	{
		path.add(step);
	}
	
	
	public void cancelPath()
	{
		path.clear();
	}
	
	
	public boolean navigateTo(Coord target)
	{
		if (target.equals(getCoord())) return true;
		PathFindingContext pfc = entity.getPathfindingContext();
		final List<PathStep> newPath = PathFinder.findPathRelative(pfc, entityPos.getCoord(), target);
		
		if (newPath == null) return false;
		cancelPath();
		addSteps(newPath);
		return true;
	}
	
	
	/**
	 * Add a move listener. If already present, do nothing.
	 */
	public void addMoveListener(EntityMoveListener listener)
	{
		moveListeners.add(listener);
	}
	
	
	public void addSteps(List<PathStep> path)
	{
		this.path.addAll(path);
	}
	
	
	public Coord getCoord()
	{
		return entityPos.getCoord();
	}
	
	
	public double getStepTime()
	{
		return stepTime;
	}
	
	
	public void setStepTime(double stepTime)
	{
		this.stepTime = stepTime;
	}


	public double getProgress()
	{
		return entityPos.getProgress();
	}


	public VectConst getVisualPos()
	{
		return entityPos.getVisualPos();
	}
	
}

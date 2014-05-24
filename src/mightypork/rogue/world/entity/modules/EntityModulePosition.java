package mightypork.rogue.world.entity.modules;


import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Move;
import mightypork.gamecore.util.math.constraints.vect.VectConst;
import mightypork.ion.IonBundle;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModule;


public class EntityModulePosition extends EntityModule {
	
	/** Last pos, will be freed upon finishing move */
	private final Coord lastPos = new Coord(0, 0);
	private boolean walking = false;
	
	private final Queue<Move> path = new LinkedList<>();
	private final EntityPos entityPos = new EntityPos();
	private double stepTime = 0.5;
	
	// marks for simple renderers
	public int lastXDir = 1;
	public int lastYDir = 1;
	
	private final Set<EntityMoveListener> moveListeners = new LinkedHashSet<>();
	
	
	public EntityModulePosition(Entity entity)
	{
		super(entity);
	}
	
	
	@Override
	public void save(IonBundle bundle)
	{
		bundle.putSequence("path", path);
		bundle.putBundled("lpos", lastPos);
		bundle.putBundled("pos", entityPos);
		bundle.put("step_time", stepTime);
	}
	
	
	@Override
	public void load(IonBundle bundle)
	{
		bundle.loadSequence("path", path);
		bundle.loadBundled("lpos", lastPos);
		bundle.loadBundled("pos", entityPos);
		
		stepTime = bundle.get("step_time", stepTime);
	}
	
	
	@Override
	public boolean isModuleSaved()
	{
		return true;
	}
	
	
	/**
	 * Set coord without animation
	 * 
	 * @param coord coord
	 */
	public void setCoord(Coord coord)
	{
		freeTile(); // release old tile
		
		entityPos.setTo(coord);
		lastPos.setTo(coord);
		cancelPath(); // discard remaining steps
		
		occupyTile();
	}
	
	
	/**
	 * Occupy current tile in level
	 */
	public void occupyTile()
	{
		if (entity.getLevel() != null) {
			entity.getLevel().occupyTile(getCoord());
		}
	}
	
	
	/**
	 * free current tile in level
	 */
	public void freeTile()
	{
		if (entity.getLevel() != null) {
			entity.getLevel().freeTile(getCoord());
		}
	}
	
	
	/**
	 * @param delta delta time
	 */
	@Override
	public void update(double delta)
	{
		if (entity.isDead()) return; // corpses dont walk
		
		if (!entityPos.isFinished()) {
			entityPos.update(delta);
		}
		
		if (walking && entityPos.isFinished()) {
			walking = false;
			
			for (final EntityMoveListener l : moveListeners) {
				l.onStepFinished();
			}
			
			if (path.isEmpty()) {
				for (final EntityMoveListener l : moveListeners) {
					l.onPathFinished();
				}
			}
		}
		
		if (!walking && !path.isEmpty()) {
			
			walking = true;
			
			final Move step = path.poll();
			
			final Coord planned = entityPos.getCoord().add(step.toCoord());
			
			if (!entity.getLevel().isWalkable(planned)) {
				cancelPath();
				
				for (final EntityMoveListener l : moveListeners) {
					l.onPathInterrupted();
				}
				
				walking = false;
			} else {
				
				// tmp for renderer
				if (step.x() != 0) this.lastXDir = step.x();
				if (step.y() != 0) this.lastYDir = step.y();
				
				freeTile();
				lastPos.setTo(entityPos.getCoord());
				entityPos.walk(step, stepTime);
				occupyTile();
			}
		}
	}
	
	
	/**
	 * @return true if path buffer is empty
	 */
	public boolean isPathFinished()
	{
		return entityPos.isFinished() && path.isEmpty();
	}
	
	
	/**
	 * Add a step to path buffer
	 * 
	 * @param step
	 */
	public void addStep(Move step)
	{
		if (path.isEmpty() && !canGoTo(step)) return;
		
		path.add(step);
	}
	
	
	/**
	 * Discard steps in buffer
	 */
	public void cancelPath()
	{
		path.clear();
	}
	
	
	/**
	 * Find path to
	 * 
	 * @param target
	 * @return path found
	 */
	public boolean navigateTo(Coord target)
	{
		if (target.equals(getCoord())) return true;
		final List<Move> newPath = entity.getPathFinder().findPathRelative(entityPos.getCoord(), target);
		
		if (newPath == null) return false;
		cancelPath();
		addSteps(newPath);
		return true;
	}
	
	
	/**
	 * Add a move listener. If already present, do nothing.
	 * 
	 * @param listener the listener
	 */
	public void addMoveListener(EntityMoveListener listener)
	{
		moveListeners.add(listener);
	}
	
	
	/**
	 * Add steps to path buffer
	 * 
	 * @param path steps
	 */
	public void addSteps(List<Move> path)
	{
		this.path.addAll(path);
	}
	
	
	/**
	 * @return coord in level
	 */
	public Coord getCoord()
	{
		return entityPos.getCoord();
	}
	
	
	/**
	 * Set step time (seconds)
	 * 
	 * @param stepTime step time
	 */
	public void setStepTime(double stepTime)
	{
		this.stepTime = stepTime;
	}
	
	
	/**
	 * @return step progress 0..1
	 */
	public double getProgress()
	{
		return entityPos.getProgress();
	}
	
	
	/**
	 * @return visual pos in level; interpolated from last to new coord
	 */
	public VectConst getVisualPos()
	{
		return entityPos.getVisualPos();
	}
	
	
	public boolean isMoving()
	{
		return walking;
	}
	
	
	public boolean hasPath()
	{
		return isMoving() || !path.isEmpty();
	}
	
	
	public boolean canGoTo(Move side)
	{
		return entity.getPathFinder().isAccessible(getCoord().add(side));
	}
	
	
	public Coord getLastPos()
	{
		return lastPos;
	}
	
}

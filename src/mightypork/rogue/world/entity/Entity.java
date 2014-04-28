package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.Sides;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.models.EntityModel;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.rogue.world.pathfinding.Heuristic;
import mightypork.rogue.world.pathfinding.PathFinder;
import mightypork.rogue.world.pathfinding.PathFindingContext;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


/**
 * World entity (mob or player)
 * 
 * @author MightyPork
 */
public final class Entity implements IonBinary {
	
	public static final int ION_MARK = 52;
	
	/** Last pos, will be freed upon finishing move */
	private final EntityPos lastPosition = new EntityPos();
	private boolean walking = false;
	
	private Level level;
	private EntityModel model;
	
	/** Entity ID */
	private int entityId = -1;
	
	/** Model ID */
	private int modelId = -1;
	
	/** Entity data holder */
	private final EntityData data = new EntityData();
	
	/** Temporary renderer's data */
	public final EntityRenderData renderData = new EntityRenderData();
	
	private final List<EntityMoveListener> moveListeners = new ArrayList<>();
	
	private final PathFindingContext pfc = new PathFindingContext() {
		
		@Override
		public boolean isAccessible(Coord pos)
		{
			return model.canWalkInto(Entity.this, pos);
		}
		
		
		@Override
		public int getMinCost()
		{
			return model.getPathMinCost();
		}
		
		
		@Override
		public Heuristic getHeuristic()
		{
			return model.getPathHeuristic();
		}
		
		
		@Override
		public int getCost(Coord from, Coord to)
		{
			return model.getPathCost(Entity.this, from, to);
		}

		
		@Override
		public Coord[] getWalkSides() {
			return Sides.cardinalSides;
		}
	};
	
	
	public Entity(int eid, EntityModel entityModel)
	{
		this.entityId = eid;
		setModel(entityModel);
		
		model.initMetadata(data);
	}
	
	
	public Entity()
	{
		// for ion
	}
	
	
	private void setModel(EntityModel entityModel)
	{
		// replace listener
		if (model != null) moveListeners.remove(model);
		moveListeners.add(entityModel);
		
		this.modelId = entityModel.id;
		this.model = entityModel;
	}
	
	
	public Level getLevel()
	{
		if (level == null) throw new IllegalStateException("Entity has no level assigned.");
		return level;
	}
	
	
	public World getWorld()
	{
		return getLevel().getWorld();
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		final IonBundle bundle = new IonBundle();
		bundle.put("model", modelId);
		bundle.put("id", entityId);
		bundle.putBundled("data", data);
		
		out.writeBundle(bundle);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		final IonBundle bundle = in.readBundle();
		modelId = bundle.get("model", 0);
		entityId = bundle.get("id", entityId);
		bundle.loadBundled("data", data);
		
		setModel(Entities.get(modelId));
	}
	
	
	/**
	 * @return unique entity id
	 */
	public int getEntityId()
	{
		return entityId;
	}
	
	
	public EntityPos getPosition()
	{
		return data.position;
	}
	
	
	public void setPosition(Coord coord)
	{
		data.position.setTo(coord);
		lastPosition.setTo(coord);
		cancelPath(); // discard remaining steps
	}
	
	
	/**
	 * @param delta delta time
	 */
	public void update(double delta)
	{
		if (!data.position.isFinished()) {
			data.position.update(delta);
		}
		
		if (walking && data.position.isFinished()) {
			walking = false;
			level.freeTile(lastPosition.getCoord());
	
			for (final EntityMoveListener l : moveListeners) {
				l.onStepFinished(this);
			}
			
			if (data.path.isEmpty()) {
				for (final EntityMoveListener l : moveListeners) {
					l.onPathFinished(this);
				}
			}
		}
		
		if (!walking && !data.path.isEmpty()) {
			
			walking = true;
			
			final PathStep step = data.path.poll();
			
			final Coord planned = data.position.getCoord().add(step.toCoord());
			
			if (!level.canWalkInto(planned)) {
				cancelPath();
				
				for (final EntityMoveListener l : moveListeners) {
					l.onPathInterrupted(this);
				}
				
				walking = false;
			} else {
				
				// tmp for renderer
				if (step.x != 0) renderData.lastXDir = step.x;
				if (step.y != 0) renderData.lastYDir = step.y;
				
				lastPosition.setTo(data.position);
				data.position.walk(step, model.getStepTime(this));
				level.occupyTile(planned);
			}
		}
		
		if (!walking) {
			model.update(this, delta);
		}
	}
	
	
	public void render(MapRenderContext context)
	{
		model.renderer.render(this, context);
	}
	
	
	public boolean isPathFinished()
	{
		return data.position.isFinished() && data.path.isEmpty();
	}
	
	
	public void addStep(PathStep step)
	{
		data.path.add(step);
	}
	
	
	public void cancelPath()
	{
		data.path.clear();
	}
	
	
	public boolean navigateTo(Coord pos)
	{
		if(pos.equals(getCoord())) return true;
		
		final List<PathStep> path = PathFinder.findPathRelative(pfc, getPosition().getCoord(), pos);
		
		if (path == null) return false;
		
		this.cancelPath();
		this.addSteps(path);
		return true;
	}
	
	
	public void addMoveListener(EntityMoveListener listener)
	{
		moveListeners.add(listener);
	}
	
	
	public void addSteps(List<PathStep> path)
	{
		data.path.addAll(path);
	}
	
	
	public void setLevel(Level level)
	{
		this.level = level;
		model.onEnteredLevel(this);
	}
	
	
	public Coord getCoord()
	{
		return data.position.getCoord();
	}
	
	
	public EntityModel getModel()
	{
		return model;
	}
}

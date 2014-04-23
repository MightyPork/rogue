package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mightypork.rogue.world.PathStep;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.models.EntityModel;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


/**
 * World entity (mob or player)
 * 
 * @author MightyPork
 */
public final class Entity implements IonBinary, IonBundled, EntityMoveListener {
	
// binary & bundled - binary stores via a bundle
	
	public static final int ION_MARK = 52;
	
	private final WorldPos position = new WorldPos(); // saved
	
	/** Entity ID */
	private int eid = 0; // saved
	
	/** Model ID */
	private int id; // saved
	
	private final Queue<PathStep> path = new LinkedList<>(); // saved
	private EntityModel model;
	public final IonBundle metadata = new IonBundle(); // saved
	
	/** Some infos for/by the renderer, not saved */
	public final EntityRenderData renderData = new EntityRenderData();
	
	private final List<EntityMoveListener> moveListeners = new ArrayList<>();
	
	// tmp flag
	private boolean walking = false;
	
	
	public Entity(int eid, WorldPos pos, EntityModel entityModel)
	{
		this.eid = eid;
		this.position.setTo(pos);
		
		setModel(entityModel);
	}
	
	
	private void setModel(EntityModel entityModel)
	{
		// replace listener
		if (model != null) moveListeners.remove(model);
		moveListeners.add(entityModel);
		
		this.id = entityModel.id;
		this.model = entityModel;
	}
	
	
	public Entity()
	{
		// for ion
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		final IonBundle ib = new IonBundle();
		save(ib);
		out.writeBundle(ib);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		load(in.readBundle());
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("id", id);
		bundle.putBundled("pos", position);
		bundle.putSequence("steps", path);
		bundle.put("eid", eid);
		
		if (model.hasMetadata()) {
			bundle.put("metadata", metadata);
		}
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		id = bundle.get("id", 0);
		
		if (model == null || id != model.id) {
			setModel(Entities.get(id));
		}
		
		bundle.loadBundled("pos", position);
		bundle.loadSequence("path", path);
		eid = bundle.get("eid", eid);
		
		if (model.hasMetadata()) {
			metadata.clear();
			bundle.loadBundle("metadata", metadata);
		}
	}
	
	
	/**
	 * @return unique entity id
	 */
	public int getEID()
	{
		return eid;
	}
	
	
	public WorldPos getPosition()
	{
		return position;
	}
	
	
	public void setPosition(WorldPos pos)
	{
		position.setTo(pos);
	}
	
	
	public void setPosition(int x, int y)
	{
		position.setTo(x, y);
		cancelPath(); // discard remaining steps
	}
	
	
	/**
	 * @param world the world
	 * @param delta delta time
	 */
	public void update(World world, Level level, double delta)
	{
		if (!position.isFinished()) {
			position.update(delta);
		}
		
		if (walking && position.isFinished()) {
			walking = false;
			
			onStepFinished(this, world, level);
			
			if (path.isEmpty()) {
				onPathFinished(this, world, level);
			}
		}
		
		if (!walking && !path.isEmpty()) {
			
			walking = true;
			
			PathStep step = path.poll();
			
			final int projX = position.x + step.x, projY = position.y + step.y;
			
			if (!level.canWalkInto(projX, projY)) {
				cancelPath();
				onPathInterrupted(this, world, level);
				walking = false;
			} else {
				
				// tmp for renderer
				if (step.x != 0) renderData.lastXDir = step.x;
				if (step.y != 0) renderData.lastYDir = step.y;
				
				position.walk(step.x, step.y, getStepTime());
				level.occupyTile(projX, projY);
				level.freeTile(position.x, position.y);
			}
		}
		
		if (!walking) {
			model.update(this, level, delta);
		}
	}
	
	
	public void render(MapRenderContext context)
	{
		model.renderer.render(this, context);
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
	
	
	protected double getStepTime()
	{
		return model.getStepTime(this);
	}
	
	
	@Override
	public void onStepFinished(Entity entity, World world, Level level)
	{
		for (final EntityMoveListener l : moveListeners) {
			l.onStepFinished(entity, world, level);
		}
	}
	
	
	@Override
	public void onPathFinished(Entity entity, World world, Level level)
	{
		for (final EntityMoveListener l : moveListeners) {
			l.onStepFinished(entity, world, level);
		}
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity, World world, Level level)
	{
		for (final EntityMoveListener l : moveListeners) {
			l.onPathInterrupted(entity, world, level);
		}
	}
	
	
	public void addMoveListener(EntityMoveListener listener)
	{
		moveListeners.add(listener);
	}
}

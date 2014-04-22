package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import mightypork.rogue.world.PathStep;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.models.EntityModel;
import mightypork.rogue.world.level.Level;
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
public final class Entity implements IonBinary, IonBundled {
	
// binary & bundled - binary stores via a bundle
	
	public static final int ION_MARK = 52;
	
	private final WorldPos position = new WorldPos();
	
	/** Entity ID */
	private int eid = 0;
	
	/** Model ID */
	private int id;
	
	private final Queue<PathStep> path = new LinkedList<>();
	private EntityModel model;
	private final IonBundle metadata = new IonBundle();
	
	
	public Entity(int eid, WorldPos pos, EntityModel entityModel)
	{
		this.eid = eid;
		this.position.setTo(pos);
		
		setModel(entityModel);
	}
	
	
	private void setModel(EntityModel entityModel)
	{
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
		bundle.put("metadata", metadata);
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
		
		metadata.clear();
		bundle.loadBundle("metadata", metadata);
	}
	
	
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
		
		if (position.isFinished()) {
			
			model.onStepFinished(this, world, level);
			
			if (!path.isEmpty()) {
				// get next step to walk
				final PathStep step = path.poll();
				position.walk(step.x, step.y, getStepTime());
			} else {
				// notify AI or whatever
				model.onPathFinished(this, world, level);
			}
		}
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
	
}

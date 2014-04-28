package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityData;
import mightypork.rogue.world.entity.renderers.PlayerRenderer;
import mightypork.rogue.world.tile.Tile;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class PlayerModel extends EntityModel {
	
	private static final double STEP_TIME = 0.25;
	
	
	public PlayerModel(int id) {
		super(id);
		setRenderer(new PlayerRenderer("player"));
	}
	
	
	@Override
	public void update(Entity entity, double delta)
	{
	}
	
	
	@Override
	public double getStepTime(Entity entity)
	{
		return STEP_TIME;
	}
	
	
	@Override
	public void onStepFinished(Entity entity)
	{
		exploreSurroundings(entity);
	}
	
	
	private void exploreSurroundings(Entity entity)
	{
		entity.getLevel().markExplored(entity.getCoord(), 4.5);
	}
	
	
	@Override
	public void onEnteredLevel(Entity entity)
	{
		exploreSurroundings(entity);
	}
	
	
	@Override
	public void onPathFinished(Entity entity)
	{
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity)
	{
	}
	
	
	@Override
	public void initMetadata(EntityData metadata)
	{
	}
	
	
	@Override
	public boolean canWalkInto(Entity entity, Coord pos)
	{
		final Tile t = entity.getLevel().getTile(pos);
		return t.isWalkable();
	}
	
	
	@Override
	public int getPathCost(Entity entity, Coord from, Coord to)
	{
		if (!entity.getLevel().getTile(entity.getCoord()).isExplored()) {
			return 1000;
		}
		
		return super.getPathCost(entity, from, to);
	}
}

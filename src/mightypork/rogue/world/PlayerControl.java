package mightypork.rogue.world;


import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityPos;
import mightypork.rogue.world.entity.PathStep;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;


public class PlayerControl {
	
	private final World world;
	
	
	public PlayerControl(World w)
	{
		this.world = w;
	}
	
	
	public Entity getEntity()
	{
		return world.getPlayerEntity();
	}
	
	
	public void goNorth()
	{
		getEntity().cancelPath();
		getEntity().addStep(PathStep.NORTH);
	}
	
	
	public void goSouth()
	{
		getEntity().cancelPath();
		getEntity().addStep(PathStep.SOUTH);
	}
	
	
	public void goEast()
	{
		getEntity().cancelPath();
		getEntity().addStep(PathStep.EAST);
	}
	
	
	public void goWest()
	{
		getEntity().cancelPath();
		getEntity().addStep(PathStep.WEST);
	}
	
	
	public void navigateTo(Coord pos)
	{
		getEntity().navigateTo(pos);
	}
	
	
	public void addMoveListener(EntityMoveListener eml)
	{
		getEntity().addMoveListener(eml);
	}
	
	
	public EntityPos getPos()
	{
		return getEntity().getPosition();
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public Level getLevel()
	{
		return world.getCurrentLevel();
	}
	
	
	public Coord getCoord()
	{
		return getEntity().getCoord();
	}
}

package mightypork.rogue.world;


import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;


public class PlayerControl {
	
	private final World world;
	
	
	public PlayerControl(World w)
	{
		this.world = w;
	}
	
	
	public void goNorth()
	{
		world.getPlayerEntity().addStep(PathStep.NORTH);
	}
	
	
	public void goSouth()
	{
		world.getPlayerEntity().addStep(PathStep.SOUTH);
	}
	
	
	public void goEast()
	{
		world.getPlayerEntity().addStep(PathStep.EAST);
	}
	
	
	public void goWest()
	{
		world.getPlayerEntity().addStep(PathStep.WEST);
	}
	
	
	public void addMoveListener(EntityMoveListener eml)
	{
		world.getPlayerEntity().addMoveListener(eml);
	}
	
	
	public WorldPos getPos()
	{
		return world.getPlayerEntity().getPosition();
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public Level getLevel()
	{
		return world.getCurrentLevel();
	}
}

package mightypork.rogue.world;


import mightypork.rogue.world.entity.models.EntityMoveListener;


public class PlayerControl {
	
	private final World world;
	
	
	public PlayerControl(World w)
	{
		this.world = w;
	}
	
	
	public void walkNorth()
	{
		world.playerEntity.addStep(PathStep.NORTH);
	}
	
	
	public void walkSouth()
	{
		world.playerEntity.addStep(PathStep.SOUTH);
	}
	
	
	public void walkEast()
	{
		world.playerEntity.addStep(PathStep.EAST);
	}
	
	
	public void walkWest()
	{
		world.playerEntity.addStep(PathStep.WEST);
	}
	
	
	public void addMoveListener(EntityMoveListener eml)
	{
		world.playerEntity.addMoveListener(eml);
	}


	public WorldPos getPos()
	{
		return world.playerEntity.getPosition();
	}


	public void walk(PathStep step)
	{
		world.playerEntity.addStep(step);
	}
}

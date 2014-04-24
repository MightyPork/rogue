package mightypork.rogue.world;


import java.util.List;

import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.pathfinding.PathCostProvider;
import mightypork.rogue.world.pathfinding.PathFinder;


public class PlayerControl {
	
	private final World world;
	
	PathCostProvider costProvider = new PathCostProvider() {
		
		@Override
		public boolean isAccessible(Coord pos)
		{
			return getLevel().canWalkInto(pos.x, pos.y);
		}
		
		
		@Override
		public int getMinCost()
		{
			return 10;
		}
		
		
		@Override
		public int getCost(Coord from, Coord to)
		{
			return 10;
		}
	};
	
	private final PathFinder pf = new PathFinder(costProvider, PathFinder.CORNER_HEURISTIC);
	
	
	public PlayerControl(World w)
	{
		this.world = w;
	}
	
	
	public void goNorth()
	{
		world.getPlayerEntity().cancelPath();
		world.getPlayerEntity().addStep(PathStep.NORTH);
	}
	
	
	public void goSouth()
	{
		world.getPlayerEntity().cancelPath();
		world.getPlayerEntity().addStep(PathStep.SOUTH);
	}
	
	
	public void goEast()
	{
		world.getPlayerEntity().cancelPath();
		world.getPlayerEntity().addStep(PathStep.EAST);
	}
	
	
	public void goWest()
	{
		world.getPlayerEntity().cancelPath();
		world.getPlayerEntity().addStep(PathStep.WEST);
	}
	
	
	public void navigateTo(WorldPos where)
	{
		final Coord start = world.getPlayerEntity().getPosition().toCoord();
		final Coord end = where.toCoord();
		final List<PathStep> path = pf.findPathRelative(start, end);
		
		if (path == null) return;
		
		world.getPlayerEntity().cancelPath();
		world.getPlayerEntity().addSteps(path);
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

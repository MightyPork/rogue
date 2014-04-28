package mightypork.rogue.world;


import java.util.HashSet;
import java.util.Set;

import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.PathStep;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.entity.modules.EntityPos;
import mightypork.rogue.world.level.Level;


public abstract class PlayerControl {
	
	protected Set<EntityMoveListener> playerMoveListeners = new HashSet<>();
	
	private World lastWorld;
	
	
	protected abstract World getWorld();
	
	
	private World getWorld2()
	{		
		World newWorld = getWorld();
		
		if (newWorld != lastWorld) {
			for (EntityMoveListener eml : playerMoveListeners) {
				newWorld.getPlayerEntity().pos.addMoveListener(eml);
			}
		}
		
		lastWorld = newWorld;
		
		return newWorld;
		
	};
	
	
	private Entity getPlayerEntity()
	{
		if(getWorld2() == null) return null;
		
		return getWorld2().getPlayerEntity();
	}
	
	
	public void goNorth()
	{
		getPlayerEntity().pos.cancelPath();
		getPlayerEntity().pos.addStep(PathStep.NORTH);
	}
	
	
	public void goSouth()
	{
		getPlayerEntity().pos.cancelPath();
		getPlayerEntity().pos.addStep(PathStep.SOUTH);
	}
	
	
	public void goEast()
	{
		getPlayerEntity().pos.cancelPath();
		getPlayerEntity().pos.addStep(PathStep.EAST);
	}
	
	
	public void goWest()
	{
		getPlayerEntity().pos.cancelPath();
		getPlayerEntity().pos.addStep(PathStep.WEST);
	}
	
	
	public void navigateTo(Coord pos)
	{
		getPlayerEntity().pos.navigateTo(pos);
	}
	
	
	public void addMoveListener(EntityMoveListener eml)
	{
		playerMoveListeners.add(eml);
		if(getPlayerEntity() != null) {
			getPlayerEntity().pos.addMoveListener(eml);
		}
	}
	
	
	public Level getLevel()
	{
		return getWorld2().getCurrentLevel();
	}
	
	
	public Coord getCoord()
	{
		return getPlayerEntity().pos.getCoord();
	}
}

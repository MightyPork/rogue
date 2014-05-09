package mightypork.rogue.world;


import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.level.LevelAccess;


public abstract class PlayerControl {
	
	protected Set<EntityMoveListener> playerMoveListeners = new HashSet<>();
	
	private World lastWorld;
	
	
	protected abstract World provideWorld();
	
	
	public World getWorld()
	{
		final World newWorld = provideWorld();
		
		if (newWorld != lastWorld) {
			for (final EntityMoveListener eml : playerMoveListeners) {
				newWorld.getPlayerEntity().pos.addMoveListener(eml);
			}
		}
		
		lastWorld = newWorld;
		
		return newWorld;
		
	}
	
	
	public Entity getPlayerEntity()
	{
		if (getWorld() == null) return null;
		
		return getWorld().getPlayerEntity();
	}
	
	
	public void goNorth()
	{
		go(Step.NORTH);
	}
	
	
	public void goSouth()
	{
		go(Step.SOUTH);
	}
	
	
	public void goEast()
	{
		go(Step.EAST);
	}
	
	
	public void goWest()
	{
		go(Step.WEST);
	}
	
	
	public void navigateTo(Coord pos)
	{
		if (!getLevel().getTile(pos).isExplored()) return;
		getPlayerEntity().pos.navigateTo(pos);
	}
	
	
	public void addMoveListener(EntityMoveListener eml)
	{
		playerMoveListeners.add(eml);
		if (getPlayerEntity() != null) {
			getPlayerEntity().pos.addMoveListener(eml);
		}
	}
	
	
	public LevelAccess getLevel()
	{
		return getWorld().getCurrentLevel();
	}
	
	
	public Coord getCoord()
	{
		return getPlayerEntity().pos.getCoord();
	}
	
	
	public boolean canGo(Step side)
	{
		return getLevel().getTile(getCoord().add(side)).isWalkable();
	}
	
	
	public boolean clickTile(Step side)
	{
		return clickTile(getCoord().add(side));
	}
	
	
	public boolean clickTile(Coord pos)
	{
		if (pos.dist(getCoord()) > 8) return false; // too far
		
		return getLevel().getTile(pos).onClick();
	}
	
	
	public void go(Step side)
	{
		getPlayerEntity().pos.cancelPath();
		getPlayerEntity().pos.addStep(side);
	}
	
	
	public boolean tryGo(Step e)
	{
		if (!canGo(e)) return false;
		go(e);
		return true;
	}
}

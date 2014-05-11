package mightypork.rogue.world;


import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.World.PlayerFacade;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.level.Level;


public abstract class PlayerControl {
	
	protected Set<EntityMoveListener> playerMoveListeners = new HashSet<>();
	
	private World lastWorld;
	
	
	/**
	 * Implementing classes should return a world instance from this method.
	 * 
	 * @return
	 */
	protected abstract World provideWorld();
	
	
	public World getWorld()
	{
		final World newWorld = provideWorld();
		
		lastWorld = newWorld;
		
		return newWorld;
		
	}
	
	
	public PlayerFacade getPlayer()
	{
		if (getWorld() == null) return null;
		
		return getWorld().getPlayer();
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
		getPlayer().navigateTo(pos);
	}
	
	
	public Level getLevel()
	{
		return getWorld().getPlayer().getLevel();
	}
	
	
	public boolean canGo(Step side)
	{
		return getLevel().getTile(getPlayer().getCoord().add(side)).isWalkable();
	}
	
	
	/**
	 * Click tile on player's side
	 * 
	 * @param side
	 * @return
	 */
	public boolean clickTile(Step side)
	{
		return clickTile(getPlayer().getCoord().add(side).toVect().add(0.5, 0.5));
	}
	
	
	public boolean clickTile(Vect pos)
	{
		//if (pos.dist(getPlayer().getVisualPos()).value() > 8) return false; // too far
		
		if (pos.dist(getPlayer().getVisualPos().add(0.5, 0.5)).value() < 1.5) {
			
			// 1st try to hit entity
			final Entity prey = getLevel().getClosestEntity(pos, EntityType.MONSTER, 1);
			if (prey != null) {
				prey.receiveAttack(getPlayer().getEntity(), getPlayer().getAttackStrength());
				return true;
			}
			
			//2nd try to click tile
			return getLevel().getTile(Coord.fromVect(pos)).onClick();
		}
		
		return false;
	}
	
	
	public void go(Step side)
	{
		getPlayer().cancelPath();
		getPlayer().addPathStep(side);
	}
	
	
	public boolean tryGo(Step e)
	{
		if (!canGo(e)) return false;
		go(e);
		return true;
	}
}

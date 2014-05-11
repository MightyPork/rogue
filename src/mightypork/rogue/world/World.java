package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.timing.Pauseable;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;
import mightypork.rogue.world.level.Level;


/**
 * World on a server. To a server, all players and levels are equal.
 * 
 * @author MightyPork
 */
public class World implements DelegatingClient, BusAccess, IonObjBundled, Pauseable {
	
	/**
	 * Convenient access to player-related methods and data
	 * 
	 * @author MightyPork
	 */
	public class PlayerFacade {
		
		
		public boolean canAscend()
		{
			return playerInfo.getLevelNumber() > 0;
		}
		
		
		public void descend()
		{
			if (!canDescend()) return;
			
			final int lvl_num = getLevelNumber();
			getLevel().removeEntity(playerEntity);
			
			playerInfo.setLevelNumber(lvl_num + 1);
			
			getLevel().addEntity(playerEntity, getLevel().getEnterPoint());
			getLevel().explore(getCoord());
		}
		
		
		public boolean canDescend()
		{
			return playerInfo.getLevelNumber() < levels.size() - 1;
		}
		
		
		public void ascend()
		{
			if (!canAscend()) return;
			
			final int lvl_num = getLevelNumber();
			getLevel().removeEntity(playerEntity);
			
			playerInfo.setLevelNumber(lvl_num - 1);
			
			getLevel().addEntity(playerEntity, getLevel().getExitPoint());
			getLevel().explore(getCoord());
		}
		
		
		/**
		 * @return current level number, zero based.
		 */
		public int getLevelNumber()
		{
			return playerInfo.getLevelNumber();
		}
		
		
		public Level getLevel()
		{
			return levels.get(playerInfo.getLevelNumber());
		}
		
		
		public int getEID()
		{
			return playerInfo.getEID();
		}
		
		
		public Coord getCoord()
		{
			return playerEntity.getCoord();
		}
		
		
		public Vect getVisualPos()
		{
			return playerEntity.pos.getVisualPos();
		}
		
		
		public void navigateTo(Coord pos)
		{
			playerEntity.pos.navigateTo(pos);
		}
		
		
		public void cancelPath()
		{
			playerEntity.pos.cancelPath();
		}
		
		
		public void addPathStep(Step step)
		{
			playerEntity.pos.addStep(step);
		}
		
		
		public boolean isMoving()
		{
			return playerEntity.pos.isMoving();
		}
		
		
		public double getMoveProgress()
		{
			return playerEntity.pos.getProgress();
		}
		
		
		public boolean isDead()
		{
			return playerEntity.isDead();
		}
		
		
		public int getHealth()
		{
			return playerEntity.health.getHealth();
		}
		
		
		public int getHealthMax()
		{
			return playerEntity.health.getMaxHealth();
		}
		
		
		public Inventory getInventory()
		{
			return playerInfo.getInventory();
		}
		
		
		public Entity getEntity()
		{
			return playerEntity;
		}
		
		
		public int getAttackStrength()
		{
			final Item weapon = playerInfo.getSelectedWeapon();
			
			if (weapon == null) return PlayerInfo.BARE_ATTACK;
			
			return Math.min(weapon.getAttackPoints(), playerInfo.BARE_ATTACK);
		}
		
		
		public boolean eatFood(Item itm)
		{
			if (itm == null || itm.isEmpty() || itm.getType() != ItemType.FOOD) return false;
			
			if (getHealth() < getHealthMax()) {
				
				playerEntity.health.addHealth(itm.getFoodPoints());
				
				return true;
			}
			
			
			return false;
		}
		
		
		public void selectWeapon(int selected)
		{
			playerInfo.selectWeapon(selected);
		}
		
		
		public int getSelectedWeapon()
		{
			return playerInfo.getSelectedWeaponIndex();
		}
		
		
		public void tryToEatSomeFood()
		{
			for (int i = 0; i < getInventory().getSize(); i++) {
				final Item itm = getInventory().getItem(i);
				if (itm == null || itm.isEmpty()) continue;
				
				final Item slice = itm.split(1);
				if (!eatFood(slice)) itm.addItem(slice);
				if (itm.isEmpty()) getInventory().setItem(i, null);
				return;
			}
		}
		
	}
	
	// not saved stuffs
	private final PlayerFacade player = new PlayerFacade();
	private Entity playerEntity;
	private BusAccess bus;
	private int pauseDepth = 0;
	
	
	private final ArrayList<Level> levels = new ArrayList<>();
	private final PlayerInfo playerInfo = new PlayerInfo();
	
	
	/** World seed */
	private long seed;
	
	/** Next entity ID */
	private int eid;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection getChildClients()
	{
		return levels;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return !isPaused();
	}
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		seed = in.get("seed", seed);
		eid = in.get("next_eid", eid);
		in.loadSequence("levels", levels);
		
		// join levels to world
		for (final Level lvl : levels) {
			lvl.setWorld(this);
		}
		
		in.loadBundled("player", playerInfo);
		
		playerEntity = levels.get(playerInfo.getLevelNumber()).getEntity(playerInfo.getEID());
		if (playerEntity == null) {
			throw new RuntimeException("Player entity not found in the world.");
		}
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.put("next_eid", eid);
		out.putSequence("levels", levels);
		out.putBundled("player", playerInfo);
	}
	
	
	public void addLevel(Level level)
	{
		levels.add(level);
		level.setWorld(this);
	}
	
	
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	public long getSeed()
	{
		return seed;
	}
	
	
	/**
	 * @return new entity ID
	 */
	public int getNewEID()
	{
		return eid++;
	}
	
	
	public void createPlayer()
	{
		if (playerInfo.isInitialized()) {
			throw new RuntimeException("Player already created.");
		}
		
		// make entity
		final int playerEid = getNewEID();
		
		final Level floor = levels.get(0);
		
		playerEntity = Entities.PLAYER.createEntity(playerEid);
		
		final Coord spawn = floor.getEnterPoint();
		
		floor.forceFreeTile(spawn);
		final Random rand = new Random(seed + 71);
		
		while (!floor.addEntity(playerEntity, spawn)) {
			spawn.x += -1 + rand.nextInt(3);
			spawn.y += -1 + rand.nextInt(3);
		}
		
		floor.explore(spawn);
		
		playerInfo.setLevelNumber(0);
		playerInfo.setEID(playerEid);
	}
	
	
	/**
	 * Attach to an event bus
	 * 
	 * @param bus event bus
	 */
	public void assignBus(BusAccess bus)
	{
		this.bus = bus;
	}
	
	
	@Override
	public EventBus getEventBus()
	{
		if (bus == null) throw new NullPointerException("World doesn't have a bus assigned.");
		return bus.getEventBus();
	}
	
	
	@Override
	public void pause()
	{
		pauseDepth++;
	}
	
	
	@Override
	public void resume()
	{
		if (pauseDepth > 0) pauseDepth--;
	}
	
	
	@Override
	public boolean isPaused()
	{
		return pauseDepth > 0;
	}
	
	
	public PlayerFacade getPlayer()
	{
		return player;
	}
	
}

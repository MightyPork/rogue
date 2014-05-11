package mightypork.rogue.world;


import java.io.IOException;
import java.util.*;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.math.Calc;
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
public class World implements DelegatingClient, BusAccess, IonObjBundled, Pauseable, Updateable {
	
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
			
			getLevel().forceFreeTile(getLevel().getEnterPoint());
			getLevel().addEntity(playerEntity, getLevel().getEnterPoint());
			getLevel().explore(getCoord());
			
			msgEnterFloor(getLevelNumber());
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
			
			getLevel().forceFreeTile(getLevel().getExitPoint());
			getLevel().addEntity(playerEntity, getLevel().getExitPoint());
			getLevel().explore(getCoord());
			
			msgEnterFloor(getLevelNumber());
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
			
			return PlayerInfo.BARE_ATTACK + weapon.getAttackPoints();
		}
		
		
		/**
		 * Eat food.
		 * 
		 * @param itm food item
		 * @return if something was eaten
		 */
		public boolean eatFood(Item itm)
		{
			if (itm == null || itm.isEmpty() || itm.getType() != ItemType.FOOD) return false;
			
			if (getHealth() < getHealthMax()) {
				
				playerEntity.health.addHealth(itm.getFoodPoints());
				itm.consume();
				
				msgEat(itm);
				
				return true;
			}
			
			return false;
		}
		
		
		public void selectWeapon(int selected)
		{
			playerInfo.selectWeapon(selected);
		}
		
		
		public Item getSelectedWeapon()
		{
			return playerInfo.getSelectedWeapon();
		}
		
		
		public int getSelectedWeaponIndex()
		{
			return playerInfo.getSelectedWeaponIndex();
		}
		
		
		public void tryToEatSomeFood()
		{
			List<Item> foods = new ArrayList<>();
			for (int i = 0; i < getInventory().getSize(); i++) {
				final Item itm = getInventory().getItem(i);
				if (itm != null && itm.getType() == ItemType.FOOD) {
					foods.add(itm);
				}
			}
			
			// sort from smallest to biggest foods
			Collections.sort(foods, new Comparator<Item>() {
				
				@Override
				public int compare(Item o1, Item o2)
				{
					return (o1.getFoodPoints() - o2.getFoodPoints());
				}
			});
			
			for (Item itm : foods) {
				if (eatFood(itm)) {
					getInventory().clean();
					return;
				}
			}
			
			msgNoMoreFood();
		}
		
		
		public void attack(Entity prey)
		{
			int attackPoints = getAttackStrength();
			
			prey.receiveAttack(getPlayer().getEntity(), attackPoints);
			
			if (prey.isDead()) {
				msgKill(prey);
			}
			
			Item wpn = getSelectedWeapon();
			
			if (wpn != null) {
				wpn.use();
				if (wpn.isEmpty()) {
					msgWeaponBreak(wpn);
					
					getInventory().clean();
					selectWeapon(-1);
					
					pickBestWeaponIfNoneSelected();
				}
			}
			
		}
		
		
		private void pickBestWeaponIfNoneSelected()
		{
			if (getSelectedWeapon() != null) return;
			
			List<Item> wpns = new ArrayList<>();
			for (int i = 0; i < getInventory().getSize(); i++) {
				final Item itm = getInventory().getItem(i);
				if (itm != null && itm.getType() == ItemType.WEAPON) {
					wpns.add(itm);
				}
			}
			
			// sort from smallest to biggest foods
			Collections.sort(wpns, new Comparator<Item>() {
				
				@Override
				public int compare(Item o1, Item o2)
				{
					return (o2.getAttackPoints() - o1.getAttackPoints());
				}
			});
			
			for (Item itm : wpns) {
				for (int i = 0; i < getInventory().getSize(); i++) {
					final Item itm2 = getInventory().getItem(i);
					if (itm2 == itm) {
						selectWeapon(i);
						break;
					}
				}
				break; // just one cycle
			}
			
			msgEquipWeapon(getSelectedWeapon());
		}
		
		
		public boolean addItem(Item item)
		{
			if (!getInventory().addItem(item)) {
				
				msgCannotPick();
				
				return false;
			}
			
			msgPick(item);
			
			if (item.getType() == ItemType.WEAPON) {
				if (getSelectedWeapon() != null) {
					if (item.getAttackPoints() > getSelectedWeapon().getAttackPoints()) {
						selectWeapon(-1); // unselect to grab the best one
					}
				}
				
				pickBestWeaponIfNoneSelected();
			}
			
			return true;
		}
	}
	
	// not saved stuffs
	private final PlayerFacade player = new PlayerFacade();
	private Entity playerEntity;
	private BusAccess bus;
	private int pauseDepth = 0;
	
	private final ArrayList<Level> levels = new ArrayList<>();
	private final PlayerInfo playerInfo = new PlayerInfo();
	
	private final WorldConsole console = new WorldConsole();
	
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
		
		msgEnterFloor(0);
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
	
	
	@Override
	public void update(double delta)
	{
		if (isPaused()) return;
		
		// update console timing
		console.update(delta);
	}
	
	
	public WorldConsole getConsole()
	{
		return console;
	}
	
	
	public void msgPick(Item item)
	{
		console.addMessage("You've picked a " + item.getVisualName() + ".");
	}
	
	
	public void msgWeaponBreak(Item item)
	{
		console.addMessage("Your " + item.getVisualName() + " has broken!");
	}
	
	
	public void msgEquipWeapon(Item item)
	{
		console.addMessage("You're now wielding " + (item == null ? "NOTHING" : "a " + item.getVisualName()) + ".");
	}
	
	
	public void msgEat(Item item)
	{
		console.addMessage("You've eaten a " + item.getVisualName() + ".");
	}
	
	
	public void msgKill(Entity prey)
	{
		console.addMessage("You've killed a " + prey.getVisualName() + ".");
	}
	
	
	public void msgDie(Entity attacker)
	{
		console.addMessage("You've been defeated by a " + attacker.getVisualName() + "!");
	}
	
	
	public void msgDiscoverSecretDoor()
	{
		console.addMessage("You've discovered a secret door.");
	}
	
	
	public void msgNoMoreFood()
	{
		console.addMessage("You have no more food!");
	}
	
	
	public void msgCannotPick()
	{
		console.addMessage("Inventory is full.");
	}
	
	
	public void msgEnterFloor(int floor)
	{
		console.addMessage("~ " + Calc.ordinal(floor + 1) + " floor ~");
	}
}

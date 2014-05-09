package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.timing.Pauseable;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.LevelAccess;


/**
 * World on a server. To a server, all players and levels are equal.
 * 
 * @author MightyPork
 */
public class World implements DelegatingClient, BusAccess, IonObjBundled, Pauseable {
	
	private final ArrayList<Level> levels = new ArrayList<>();
	
	private final PlayerInfo playerInfo = new PlayerInfo();
	private Entity playerEntity;
	
	private BusAccess bus;
	
	/** World seed */
	private long seed;
	
	/** Next entity ID */
	private int eid;
	
	private boolean paused;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection getChildClients()
	{
		return levels;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return !paused;
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
		
		playerEntity = levels.get(playerInfo.getLevel()).getEntity(playerInfo.getEID());
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
	
	
	public void createPlayer(int level)
	{
		if (playerInfo.isInitialized()) {
			throw new RuntimeException("Player already created.");
		}
		
		// make entity
		final int playerEid = getNewEID();
		
		final Level floor = levels.get(level);
		if (floor == null) {
			throw new IndexOutOfBoundsException("No such level: " + level);
		}
		
		playerEntity = Entities.PLAYER.createEntity(playerEid);
		
		final Coord spawn = floor.getEnterPoint();
		
		floor.forceFreeTile(spawn);
		floor.addEntity(playerEntity, spawn);
		floor.explore(spawn);
		
		playerInfo.setLevel(level);
		playerInfo.setEID(playerEid);
	}
	
	
	public LevelAccess getCurrentLevel()
	{
		return levels.get(playerInfo.getLevel());
	}
	
	
	public Entity getPlayerEntity()
	{
		return playerEntity;
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
		paused = true;
	}
	
	
	@Override
	public void resume()
	{
		paused = false;
	}
	
	
	@Override
	public boolean isPaused()
	{
		return paused;
	}
	
}

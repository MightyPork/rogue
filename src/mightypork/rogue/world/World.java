package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.error.CorruptDataException;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.timing.Pauseable;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;


/**
 * World object.
 * 
 * @author MightyPork
 */
public class World implements DelegatingClient, BusAccess, IonObjBundled, Pauseable, Updateable {
	
	// not saved stuffs
	private final PlayerFacade playerFacade = new PlayerFacade(this);
	
	final WorldConsole console = new WorldConsole();
	Entity playerEntity;
	private BusAccess bus;
	private int pauseDepth = 0;
	
	/** List of world's levels */
	final ArrayList<Level> levels = new ArrayList<>();
	
	/** Player data saved together with world */
	final PlayerData playerData = new PlayerData();
	
	/** World seed */
	private long seed;
	
	/** Next entity ID */
	private int eid;
	
	private File saveFile;
	
	
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
		
		in.loadBundled("player", playerData);
		
		final int eid = playerData.getEID();
		final int lvl = playerData.getLevelNumber();
		
		playerEntity = levels.get(lvl).getEntity(eid);
		if (playerEntity == null) {
			
			Log.e("Player entity not found in the world: " + eid + " on floor " + lvl);
			
			for (int i = 0; i < levels.size(); i++) {
				final Entity ent = levels.get(i).getEntity(eid);
				if (ent != null) {
					Log.f3("Player entity was really on floor: " + i);
					break;
				}
			}
			
			throw new CorruptDataException("Player not found in world.");
		}
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.put("next_eid", eid);
		out.putSequence("levels", levels);
		out.putBundled("player", playerData);
		
		// used for peking in before actually loading the world.
		out.put("meta.last_level", playerData.getLevelNumber());
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
		if (playerData.isInitialized()) {
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
		
		playerData.setLevelNumber(0);
		playerData.setEID(playerEid);
		
		console.msgEnterFloor(0);
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
		return playerFacade;
	}
	
	
	@Override
	public void update(double delta)
	{
		if (isPaused()) return;
		
		// update console timing - not as child client
		console.update(delta);
		
		if (saveFile == null) Log.w("World has no save file.");
	}
	
	
	/**
	 * @return world console
	 */
	public WorldConsole getConsole()
	{
		return console;
	}
	
	
	/**
	 * Set file for saving
	 * 
	 * @param file save file
	 */
	public void setSaveFile(File file)
	{
		this.saveFile = file;
	}
	
	
	/**
	 * @return assigned file fro saving
	 */
	public File getSaveFile()
	{
		return saveFile;
	}
}

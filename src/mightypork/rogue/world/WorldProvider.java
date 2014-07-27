package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;

import mightypork.rogue.world.gen.WorldCreator;
import mightypork.rogue.world.level.Level;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.ion.Ion;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.logging.Log;


/**
 * Global singleton world holder and storage
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class WorldProvider extends BusNode {
	
	public static synchronized void init()
	{
		if (inst == null) {
			inst = new WorldProvider();
		}
	}
	
	
	public WorldProvider()
	{
		setListening(false);
	}
	
	private static WorldProvider inst;
	
	
	public static WorldProvider get()
	{
		if (inst == null) {
			throw new IllegalStateException("World provider not initialized.");
		}
		
		return inst;
	}
	
	private World world;
	private final PlayerControl playerControl = new PlayerControl() {
		
		@Override
		protected World provideWorld()
		{
			return world;
		}
	};
	
	
	/**
	 * Create and register a world based on a seed
	 * 
	 * @param seed random seed
	 * @return the world
	 */
	public World createWorld(long seed)
	{
		Log.f2("Creating a new world with seed " + seed);
		final World w = WorldCreator.createWorld(seed);
		setWorld(w);
		return w;
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public void setWorld(World newWorld)
	{
		if (world != null) removeChildClient(world);
		world = newWorld;
		
		if (newWorld == null) return;
		
		addChildClient(world);
	}
	
	
	public void loadWorld(File file) throws IOException
	{
		Log.f2("Loading world from: " + file);
		
		final IonDataBundle bu = Ion.fromFile(file);
		setWorld(bu.loadBundled("world", new World()));
		world.setSaveFile(file);
	}
	
	
	public void saveWorld(File file) throws IOException
	{
		if (world == null) {
			throw new IllegalStateException("Trying to save a NULL world.");
		}
		if (file == null) {
			throw new IllegalStateException("Trying to save world to a NULL file.");
		}
		
		if (world.isGameOver()) {
			throw new IllegalStateException("Cannot save, player is dead.");
		}
		
		Log.f2("Saving world to: " + file);
		
		final IonDataBundle bu = new IonDataBundle();
		bu.put("level", world.getPlayer().getLevelNumber());
		bu.putBundled("world", world);
		
		Ion.toFile(file, bu);
	}
	
	
	/**
	 * Save to world's assigned save file.
	 * 
	 * @throws IOException
	 */
	public void saveWorld() throws IOException
	{
		saveWorld(world.getSaveFile());
	}
	
	
	public Level getCurrentLevel()
	{
		return getWorld().getPlayer().getLevel();
	}
	
	
	public PlayerFacade getPlayer()
	{
		return getWorld().getPlayer();
	}
	
	
	/**
	 * @return constant player control (world independent)
	 */
	public PlayerControl getPlayerControl()
	{
		return playerControl;
	}
	
}

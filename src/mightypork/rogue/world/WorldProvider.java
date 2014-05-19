package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.world.gen.WorldCreator;
import mightypork.rogue.world.level.Level;


/**
 * Global singleton world holder and storage
 * 
 * @author MightyPork
 */
public class WorldProvider extends RootBusNode {
	
	public static synchronized void init(BusAccess busAccess)
	{
		if (inst == null) {
			inst = new WorldProvider(busAccess);
		}
	}
	
	
	public WorldProvider(BusAccess busAccess)
	{
		super(busAccess);
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
		final World w = WorldCreator.createWorld(seed);
		setWorld(w);
		return w;
	}
	
	
	/**
	 * Destroy world, set to null.
	 */
	public void destroyWorld()
	{
		setWorld(null);
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
		
		world.assignBus(this); // connect to bus (for event dispatching)
		addChildClient(world);
	}
	
	
	public void loadWorld(File file) throws IOException
	{
		setWorld(Ion.fromFile(file, World.class));
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
		
		if(world.getPlayer().isDead()) {
			throw new IllegalStateException("Cannot save, player is dead.");
		}
		
		Ion.toFile(file, world);
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

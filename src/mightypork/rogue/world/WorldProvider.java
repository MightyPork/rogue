package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.world.gen.WorldCreator;
import mightypork.rogue.world.level.Level;


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
	
	
	public void createWorld(long seed)
	{
		setWorld(WorldCreator.createWorld(seed));
	}
	
	
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
	}
	
	
	public void saveWorld(File file) throws IOException
	{
		if (world == null) {
			throw new IllegalStateException("Trying to save a NULL world.");
		}
		Ion.toFile(file, world);
	}
	
	
	public void saveWorld() throws IOException
	{
		if (world == null) {
			throw new IllegalStateException("Trying to save a NULL world.");
		}
		
		final File f = world.getSaveFile();
		
		if (f == null) {
			throw new IllegalStateException("Trying to save world to a NULL file.");
		}
		
		Ion.toFile(f, world);
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

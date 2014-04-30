package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.world.entity.Entity;
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
		if (inst == null) { throw new IllegalStateException("World provider not initialized."); }
		
		return inst;
	}
	
	private World world;
	private final PlayerControl playerControl = new PlayerControl() {
		
		@Override
		protected World getWorld()
		{
			return world;
		}
	};
	
	
	public void createWorld(long seed)
	{
		setWorld(WorldCreator.createWorld(seed));
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	private void setWorld(World newWorld)
	{
		
		if (world != null) removeChildClient(world);
		world = newWorld;
		addChildClient(world);
	}
	
	
	public void loadWorld(File file) throws IOException
	{
		setWorld(Ion.fromFile(file, World.class));
	}
	
	
	public void saveWorld(File file) throws IOException
	{
		if (world == null) throw new IllegalStateException("Trying to save a NULL world.");
		Ion.toFile(file, world);
	}
	
	
	public Level getCurrentLevel()
	{
		return getWorld().getCurrentLevel();
	}
	
	
	public Entity getPlayerEntity()
	{
		return getWorld().getPlayerEntity();
	}
	
	
	/**
	 * @return constant player control (world independent)
	 */
	public PlayerControl getPlayerControl()
	{
		return playerControl;
	}
	
	
	@Override
	protected void deinit()
	{
	}
	
}

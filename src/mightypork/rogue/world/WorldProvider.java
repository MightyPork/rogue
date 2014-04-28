package mightypork.rogue.world;


import java.io.File;
import java.io.IOException;

import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.util.control.eventbus.BusAccess;
import mightypork.util.control.eventbus.clients.BusNode;
import mightypork.util.control.eventbus.clients.RootBusNode;
import mightypork.util.files.ion.Ion;
import mightypork.util.timing.Updateable;


public class WorldProvider extends RootBusNode implements Updateable {
	
	public static synchronized void init(BusAccess busAccess)
	{
		if (inst == null) {
			inst = new WorldProvider(busAccess);
		}
	}
	
	
	public WorldProvider(BusAccess busAccess) {
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
		protected World getWorld()
		{
			return world;
		}
	};
	
	
	public void createWorld(long seed)
	{
		if (world != null) removeChildClient(world);
		world = WorldCreator.createWorld(seed);
		addChildClient(world);
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public void loadWorld(File file) throws IOException
	{
		world = Ion.fromFile(file, World.class);
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
	public void update(double delta)
	{
		world.update(delta);
	}
	
	
	@Override
	protected void deinit()
	{
	}
	
}

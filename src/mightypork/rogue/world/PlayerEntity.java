package mightypork.rogue.world;


import java.io.IOException;

import mightypork.rogue.world.map.MapObserver;
import mightypork.util.ion.IonBundle;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class PlayerEntity extends WorldEntity implements MapObserver {
	
	public static final double PLAYER_STEP_TIME = 0.3;
	
	public boolean connected = false;
	
	
	public PlayerEntity() {
		super();
	}
	
	
	public PlayerEntity(ServerWorld world, int x, int y, int floor) {
		super(world, new WorldPos(x, y, floor));
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		super.load(bundle);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		super.save(bundle);
	}
	
	
	@Override
	public int getViewRange()
	{
		return 15;
	}
	
	
	@Override
	protected double getStepTime()
	{
		return PLAYER_STEP_TIME;
	}
	
	
	public boolean isConnected()
	{
		return connected;
	}
	
	
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}


	@Override
	public WorldPos getViewPosition()
	{
		return getPosition();
	}
	
	@Override
	public boolean isPhysical()
	{
		return isConnected();
	}
}

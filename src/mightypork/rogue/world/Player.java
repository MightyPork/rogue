package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class Player implements IonBinary, MapObserver {
	
	public static final short ION_MARK = 0;
	
	private final double walktime = 0.3; // possibly make changeable for speed potion
	
	private final WorldPos position = new WorldPos();
	private final WorldPos target = new WorldPos();
	private Runnable moveListenerCustom;
	private Runnable moveListener = new Runnable() {
		
		@Override
		public void run()
		{
			if (moveListenerCustom != null) moveListenerCustom.run();
			
			if (!target.equals(position)) {
				
				int x = (target.x - position.x);
				int y = (target.y - position.y);
				
				if (Math.abs(x) >= Math.abs(y)) y = 0;
				if (Math.abs(y) > Math.abs(x)) x = 0;
				
				if (x > 0) x = 1;
				if (x < 0) x = -1;
				
				if (y > 0) y = 1;
				if (y < 0) y = -1;
				
				position.walk(x, y, walktime);
				
			}
		}
	};
	
	
	public Player()
	{
		position.setMoveListener(moveListener);
	}
	
	
	public Player(int x, int y, int floor)
	{
		position.setTo(x, y, floor);
		target.setTo(position);
	}
	
	
	public Player(WorldPos pos)
	{
		this(pos.x, pos.y, pos.floor);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		IonBundle ib = in.readBundle();
		ib.loadBundled("pos", position);
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		IonBundle ib = new IonBundle();
		ib.putBundled("target", target);
		out.writeBundle(ib);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public WorldPos getPosition()
	{
		return position;
	}
	
	
	@Override
	public int getViewRange()
	{
		return 15;
	}
	
	
	public void teleport(WorldPos pos)
	{
		position.setTo(pos);
		target.setTo(pos);
	}
	
	
	public void walk(int offsetX, int offsetY)
	{
		target.setTo(position.x + offsetX, position.y + offsetY, this.position.floor);
	}
	
	
	public void setMoveListener(Runnable r)
	{
		this.moveListenerCustom = r;
	}
	
	
	public void updateVisual(double delta)
	{
		position.update(delta);
	}
	
	
	public void updateLogic(double delta)
	{
		// server stuffs (sleep timer etc)
	}
}

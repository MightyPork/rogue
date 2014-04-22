package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.mutable.VectAnimated;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;
import mightypork.util.math.Easing;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class Player implements IonBundled, MapObserver, Updateable {
	
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
	public void load(IonBundle in) throws IOException
	{
		in.loadBundled("pos", position);
		in.loadBundled("target", target);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.putBundled("pos", position);
		out.putBundled("target", target);
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
	
	
	@Override
	public void update(double delta)
	{
		position.update(delta);
	}
	
	
	public void setMoveListener(Runnable r)
	{
		this.moveListenerCustom = r;
	}
}

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
	
	private final WorldPos position = new WorldPos();
	private final VectAnimated walkOffset = new VectAnimated(Vect.ZERO, Easing.LINEAR);
	private final WorldPos target = new WorldPos();
	private Runnable targetListener;
	
	
	public Player()
	{
		walkOffset.setDefaultDuration(0.25);
	}
	
	
	public Player(int x, int y, int floor)
	{
		this.position.setTo(x, y, floor);
		this.target.setTo(position);
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
	public WorldPos getLogicalPosition()
	{
		return position;
	}
	
	
	@Override
	public Vect getVisualOffset()
	{
		return walkOffset;
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
		walkOffset.reset();
	}
	
	
	public void walk(int offsetX, int offsetY)
	{
		this.target.setTo(position.x + offsetX, position.y + offsetY, this.position.floor);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!walkOffset.isFinished()) {
			walkOffset.update(delta);
			if (walkOffset.isFinished()) {
				position.add(walkOffset.xi(), walkOffset.yi(), position.floor);
				walkOffset.reset();
				targetListener.run();
			}
		}
		
		if (walkOffset.isFinished() && !target.equals(position)) {
			
			int x = (target.x - position.x);
			int y = (target.y - position.y);
			
			if (Math.abs(x) >= Math.abs(y)) y = 0;
			if (Math.abs(y) > Math.abs(x)) x = 0;
			
			if (x > 0) x = 1;
			if (x < 0) x = -1;
			
			if (y > 0) y = 1;
			if (y < 0) y = -1;
			
			walkOffset.animate(x, y, 0);
		}
	}
	
	
	public void setTargetListener(Runnable r)
	{
		this.targetListener = r;
	}
}

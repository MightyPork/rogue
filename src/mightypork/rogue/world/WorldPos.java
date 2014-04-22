package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.mutable.VectAnimated;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;
import mightypork.util.math.Easing;


/**
 * A simple dimension data object
 * 
 * @author MightyPork
 */
public class WorldPos implements IonBundled, Updateable {
	
	public int x, y;
	private final VectAnimated walkOffset = new VectAnimated(Vect.ZERO, Easing.LINEAR);
	
	
	public WorldPos(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public WorldPos()
	{
	}
	
	
	public double getProgress()
	{
		return walkOffset.getProgress();
	}
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		x = in.get("x", 0);
		y = in.get("y", 0);
		walkOffset.reset();
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("x", x);
		out.put("y", y);
	}
	
	
	public double getX()
	{
		return x;
	}
	
	
	public double getY()
	{
		return y;
	}
	
	
	public double getVisualX()
	{
		return x + walkOffset.x();
	}
	
	
	public double getVisualY()
	{
		return y + walkOffset.y();
	}
	
	
	public void setTo(int x, int y)
	{
		this.x = x;
		this.y = y;
		walkOffset.reset();
	}
	
	
	public void setTo(WorldPos other)
	{
		setTo(other.x, other.y);
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof WorldPos)) return false;
		final WorldPos other = (WorldPos) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}
	
	
	public void walk(int x, int y, double secs)
	{
		setTo(this.x + x, this.y + y);
		walkOffset.setTo(-x, -y);
		walkOffset.animate(0, 0, 0, secs);
	}
	
	
	@Override
	public void update(double delta)
	{
		walkOffset.update(delta);
	}
	
	
	public boolean isFinished()
	{
		return walkOffset.isFinished();
	}
	
}

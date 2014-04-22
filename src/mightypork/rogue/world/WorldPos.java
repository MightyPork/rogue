package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


/**
 * A simple dimension data object
 * 
 * @author MightyPork
 */
public class WorldPos implements IonBundled {
	
	public int x, y, floor;
	
	
	public WorldPos(int x, int y, int floor)
	{
		super();
		this.x = x;
		this.y = y;
		this.floor = floor;
	}
	
	
	public WorldPos()
	{
	}
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		x = in.get("x", 0);
		y = in.get("y", 0);
		floor = in.get("z", 0);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("x", x);
		out.put("y", y);
		out.put("z", floor);
	}
	
	
	public void setTo(int x, int y, int floor)
	{
		this.x = x;
		this.y = y;
		this.floor = floor;
	}
	
	
	public void setTo(WorldPos other)
	{
		this.x = other.x;
		this.y = other.y;
		this.floor = other.floor;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + floor;
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
		if (floor != other.floor) return false;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}
	
	
	public void add(int x, int y, int floor)
	{
		setTo(this.x + x, this.y + y, this.floor + floor);
	}
	
}

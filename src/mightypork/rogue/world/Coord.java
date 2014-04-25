package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.annotations.FactoryMethod;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonBundled;


/**
 * Coordinate
 * 
 * @author MightyPork
 */
public class Coord implements IonBundled {
	
	public int x;
	public int y;
	
	
	@FactoryMethod
	public static Coord make(int x, int y)
	{
		return new Coord(x, y);
	}
	
	
	@FactoryMethod
	public static Coord make(Coord other)
	{
		return new Coord(other);
	}
	
	
	@FactoryMethod
	public static Coord zero()
	{
		return make(0, 0);
	}
	
	
	public Coord(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public Coord(Coord other)
	{
		this.x = other.x;
		this.y = other.y;
	}
	
	
	public Coord add(int addX, int addY)
	{
		return new Coord(x + addX, y + addY);
	}
	
	
	public Coord add(Coord other)
	{
		return add(other.x, other.y);
	}
	
	
	public Coord copy()
	{
		return make(this);
	}
	
	
	public void setTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public void setTo(Coord pos)
	{
		setTo(pos.x, pos.y);
	}
	
	
	/**
	 * Check if coord is in a range (inclusive)
	 */
	public boolean isInRange(int x0, int y0, int x1, int y1)
	{
		return !(x < x0 || x > x1 || y < y0 || y > y1);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		x = bundle.get("x", 0);
		y = bundle.get("y", 0);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("x", x);
		bundle.put("y", y);
	}
	
	
	@Override
	public String toString()
	{
		return "Coord(" + x + "," + y + ")";
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
		if (!(obj instanceof Coord)) return false;
		final Coord other = (Coord) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}
}

package mightypork.rogue.world;


import mightypork.util.annotations.FactoryMethod;


// coord
public class Coord {
	
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

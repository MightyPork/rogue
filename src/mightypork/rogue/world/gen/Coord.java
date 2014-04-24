package mightypork.rogue.world.gen;


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
	
	
	public Coord copy()
	{
		return make(this);
	}
	
	@Override
	public String toString()
	{
		return "Coord("+x+","+y+")";
	}
}

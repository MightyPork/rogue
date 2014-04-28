package mightypork.rogue.world;


public class Sides {
	
	public static final byte NW = (byte) 0b10000000;
	public static final byte N = 0b01000000;
	public static final byte NE = 0b00100000;
	public static final byte E = 0b00010000;
	public static final byte SE = 0b00001000;
	public static final byte S = 0b00000100;
	public static final byte SW = 0b00000010;
	public static final byte W = 0b00000001;
	
	public static final byte CARDINAL = N | S | E | W;
	public static final byte DIAGONAL = NE | NW | SE | SW;
	
	public static final byte NW_CORNER = W | NW | N;
	public static final byte NE_CORNER = E | NE | N;
	public static final byte SW_CORNER = W | SW | S;
	public static final byte SE_CORNER = E | SE | S;
	
	//@formatter:off	
	public final static Coord[] allSides = {
			Coord.make(-1, -1),
			Coord.make(0, -1),
			Coord.make(1, -1),
			Coord.make(1, 0),
			Coord.make(1, 1),
			Coord.make(0, 1),
			Coord.make(-1, 1),
			Coord.make(-1, 0)
	};
	
	public final static Coord[] cardinalSides = {
		Coord.make(0, -1),
		Coord.make(1, 0),
		Coord.make(0, 1),
		Coord.make(-1, 0)
	};
	
	//@formatter:on
	
	/**
	 * Get element from all sides
	 * 
	 * @param i side index
	 * @return the side coord
	 */
	public static Coord get(int i)
	{
		return allSides[i]; // FIXME Coord is mutable
	}
	
	
	public static byte bit(int i)
	{
		return (byte) (1 << (7 - i));
	}
}

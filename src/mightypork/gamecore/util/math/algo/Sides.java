package mightypork.gamecore.util.math.algo;


public class Sides {
	
	public static final byte NW = (byte) 0b10000000;
	public static final byte N = (byte) 0b01000000;
	public static final byte NE = (byte) 0b00100000;
	public static final byte E = (byte) 0b00010000;
	public static final byte SE = (byte) 0b00001000;
	public static final byte S = (byte) 0b00000100;
	public static final byte SW = (byte) 0b00000010;
	public static final byte W = (byte) 0b00000001;
	
	public static final byte CARDINAL = N | S | E | W;
	public static final byte DIAGONAL = NE | NW | SE | SW;
	
	public static final byte NW_CORNER = W | NW | N;
	public static final byte NE_CORNER = E | NE | N;
	public static final byte SW_CORNER = W | SW | S;
	public static final byte SE_CORNER = E | SE | S;
	
	//@formatter:off	
	public final static Step[] allSides = {
		Step.make(-1, -1),
		Step.make(0, -1),
		Step.make(1, -1),
		Step.make(1, 0),
		Step.make(1, 1),
		Step.make(0, 1),
		Step.make(-1, 1),
		Step.make(-1, 0)
	};
	
	public final static Step[] cardinalSides = {
		Step.make(0, -1),
		Step.make(1, 0),
		Step.make(0, 1),
		Step.make(-1, 0)
	};
	
	//@formatter:on
	
	/**
	 * Get element from all sides
	 * 
	 * @param i side index
	 * @return the side coord
	 */
	public static Step get(int i)
	{
		return allSides[i];
	}
	
	
	public static byte bit(int i)
	{
		return (byte) (1 << (7 - i));
	}
}

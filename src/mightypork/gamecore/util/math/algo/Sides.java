package mightypork.gamecore.util.math.algo;


public class Sides {
	
	public static final byte MASK_NW = (byte) 0b10000000;
	public static final byte MASK_N = (byte) 0b01000000;
	public static final byte MASK_NE = (byte) 0b00100000;
	public static final byte MASK_E = (byte) 0b00010000;
	public static final byte MASK_SE = (byte) 0b00001000;
	public static final byte MASK_S = (byte) 0b00000100;
	public static final byte MASK_SW = (byte) 0b00000010;
	public static final byte MASK_W = (byte) 0b00000001;
	
	public static final byte MASK_CARDINAL = MASK_N | MASK_S | MASK_E | MASK_W;
	public static final byte MASK_DIAGONAL = MASK_NE | MASK_NW | MASK_SE | MASK_SW;
	
	public static final byte NW_CORNER = MASK_W | MASK_NW | MASK_N;
	public static final byte NE_CORNER = MASK_E | MASK_NE | MASK_N;
	public static final byte SW_CORNER = MASK_W | MASK_SW | MASK_S;
	public static final byte SE_CORNER = MASK_E | MASK_SE | MASK_S;
	
	public static final Step NW = Step.make(-1, -1);
	public static final Step N = Step.make(0, -1);
	public static final Step NE = Step.make(1, -1);
	public static final Step E = Step.make(1, 0);
	public static final Step SE = Step.make(1, 1);
	public static final Step S = Step.make(0, 1);
	public static final Step SW = Step.make(-1, 1);
	public static final Step W = Step.make(-1, 0);
	
	//@formatter:off	
	/** All sides, in the order of bits. */
	public final static Step[] ALL_SIDES = {
		NW,
		N,
		NE,
		E,
		SE,
		S,
		SW,
		W
	};
	
	public final static Step[] CARDINAL_SIDES = {
		N,
		E,
		S,
		W
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
		return ALL_SIDES[i];
	}
	
	
	public static byte bit(int i)
	{
		return (byte) (1 << (7 - i));
	}
}

package mightypork.gamecore.util.math.algo;


import java.io.IOException;

import mightypork.ion.IonInput;
import mightypork.ion.IonObjBinary;
import mightypork.ion.IonOutput;


/**
 * Path step.<br>
 * Must be binary in order to be saveable in lists.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Move implements IonObjBinary {
	
	public static final int ION_MARK = 254;
	
	public static final Move NORTH = new Move(0, -1);
	public static final Move SOUTH = new Move(0, 1);
	public static final Move EAST = new Move(1, 0);
	public static final Move WEST = new Move(-1, 0);
	public static final Move NONE = new Move(0, 0);
	
	
	public static Move make(int x, int y)
	{
		x = x < 0 ? -1 : x > 0 ? 1 : 0;
		y = y < 0 ? -1 : y > 0 ? 1 : 0;
		
		if (y == -1 && x == 0) return NORTH;
		if (y == 1 && x == 0) return SOUTH;
		if (x == -1 && y == 0) return WEST;
		if (x == 1 && y == 0) return EAST;
		if (x == 0 && y == 0) return NONE;
		
		return new Move(x, y);
	}
	
	private byte x;
	private byte y;
	
	
	public Move()
	{
		// for ion
	}
	
	
	public Move(int x, int y)
	{
		this.x = (byte) (x < 0 ? -1 : x > 0 ? 1 : 0);
		this.y = (byte) (y < 0 ? -1 : y > 0 ? 1 : 0);
	}
	
	
	public int x()
	{
		return x;
	}
	
	
	public int y()
	{
		return y;
	}
	
	
	public Coord toCoord()
	{
		return Coord.make(x, y);
	}
	
	
	@Override
	public String toString()
	{
		return "(" + x + ";" + y + ")";
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		x = in.readByte();
		y = in.readByte();
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeByte(x);
		out.writeByte(y);
	}
}

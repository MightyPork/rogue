package mightypork.gamecore.util.math.algo;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBinary;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.ion.IonOutput;


/**
 * Path step.<br>
 * Must be binary in order to be saveable in lists.
 * 
 * @author MightyPork
 */
public class Step implements IonObjBinary, IonObjBundled {
	
	public static final int ION_MARK = 254;
	
	public static final Step NORTH = new Step(0, -1);
	public static final Step SOUTH = new Step(0, 1);
	public static final Step EAST = new Step(1, 0);
	public static final Step WEST = new Step(-1, 0);
	public static final Step NONE = new Step(0, 0);
	
	
	public static Step make(int x, int y)
	{
		x = x < 0 ? -1 : x > 0 ? 1 : 0;
		y = y < 0 ? -1 : y > 0 ? 1 : 0;
		
		if (y == -1 && x == 0) return NORTH;
		if (y == 1 && x == 0) return SOUTH;
		if (x == -1 && y == 0) return WEST;
		if (x == 1 && y == 0) return EAST;
		if (x == 0 && y == 0) return NONE;
		
		return new Step(x, y);
	}
	
	private byte x;
	private byte y;
	
	
	public Step()
	{
		// for ion
	}
	
	
	public Step(int x, int y)
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
	public short getIonMark()
	{
		return ION_MARK;
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
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		x = bundle.get("x", x);
		y = bundle.get("y", y);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("x", x);
		bundle.put("y", y);
	}
	
}

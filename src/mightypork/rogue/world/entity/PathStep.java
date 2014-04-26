package mightypork.rogue.world.entity;


import java.io.IOException;

import mightypork.rogue.world.Coord;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


/**
 * Path step.<br>
 * Must be binary in order to be saveable in lists.
 * 
 * @author MightyPork
 */
public class PathStep implements IonBinary {
	
	public static final PathStep NORTH = new PathStep(0, -1);
	public static final PathStep SOUTH = new PathStep(0, 1);
	public static final PathStep EAST = new PathStep(1, 0);
	public static final PathStep WEST = new PathStep(-1, 0);
	public static final PathStep NONE = new PathStep(0, 0);
	
	
	public static PathStep make(int x, int y)
	{
		x = x < 0 ? -1 : x > 0 ? 1 : 0;
		y = y < 0 ? -1 : y > 0 ? 1 : 0;
		
		if (y == -1) return NORTH;
		if (y == 1) return SOUTH;
		if (x == -1) return WEST;
		if (x == 1) return EAST;
		
		return NONE;
	}
	
	public static final int ION_MARK = 0;
	
	public int x;
	public int y;
	
	
	public PathStep(int x, int y)
	{
		this.x = x < 0 ? -1 : x > 0 ? 1 : 0;
		this.y = y < 0 ? -1 : y > 0 ? 1 : 0;
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
	
}

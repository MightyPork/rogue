package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


public class PathStep implements IonBinary {
	
	public static final PathStep NORTH = new PathStep(0, -1);
	public static final PathStep SOUTH = new PathStep(0, 1);
	public static final PathStep EAST = new PathStep(1, 0);
	public static final PathStep WEST = new PathStep(-1, 0);
	public static final PathStep NORTH_EAST = new PathStep(1, -1);
	public static final PathStep NORTH_WEST = new PathStep(-1, -1);
	public static final PathStep SOUTH_EAST = new PathStep(1, 1);
	public static final PathStep SOUTH_WEST = new PathStep(-1, 1);
	public static final PathStep NONE = new PathStep(0, 0);
	
	
	public static PathStep make(int x, int y)
	{
		x = x < 0 ? -1 : x > 0 ? 1 : 0;
		y = y < 0 ? -1 : y > 0 ? 1 : 0;
		
		if (x == 0 && y == -1) return NORTH;
		if (x == 0 && y == 1) return SOUTH;
		if (x == -1 && y == 0) return WEST;
		if (x == 1 && y == 0) return EAST;
		
		if (x == -1 && y == -1) return NORTH_WEST;
		if (x == 1 && y == -1) return NORTH_EAST;
		if (x == -1 && y == 1) return SOUTH_WEST;
		if (x == 1 && y == 1) return SOUTH_EAST;
		
		if (x == 0 && y == 0) return NONE;
		
		return new PathStep(x, y);
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
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}

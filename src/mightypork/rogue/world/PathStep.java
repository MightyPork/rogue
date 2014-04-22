package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


public class PathStep implements IonBinary {
	
	public static final int ION_MARK = 0;
	
	public int x;
	public int y;
	
	
	public PathStep(int x, int y) {
		this.x = x < 1 ? -1 : x > 0 ? 1 : 0;
		this.y = y < 1 ? -1 : y > 0 ? 1 : 0;
		
		y = (int) Math.signum(x);
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

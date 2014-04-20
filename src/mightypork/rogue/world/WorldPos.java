package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


/**
 * A simple dimension data object
 * 
 * @author MightyPork
 */
public class WorldPos implements Ionizable {
	
	public static final short ION_MARK = 707;
	
	public int x, y, floor;
	
	
	public WorldPos(int x, int y, int z)
	{
		super();
		this.x = x;
		this.y = y;
		this.floor = z;
	}
	
	
	@IonConstructor
	public WorldPos()
	{
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		x = Ion.readInt(in);
		y = Ion.readInt(in);
		floor = Ion.readInt(in);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeInt(out, x);
		Ion.writeInt(out, y);
		Ion.writeInt(out, floor);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void setTo(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.floor = z;
	}
}

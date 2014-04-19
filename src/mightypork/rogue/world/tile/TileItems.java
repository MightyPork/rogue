package mightypork.rogue.world.tile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Ionizable;


public class TileItems extends Stack<Item> implements Ionizable {
	
	public static final short ION_MARK = 703;
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		Ion.readSequence(in, this);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeSequence(out, this);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}

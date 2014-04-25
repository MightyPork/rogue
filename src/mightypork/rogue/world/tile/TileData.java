package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.util.error.YouFuckedUpException;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


public class TileData implements IonBinary {
	
	private static final byte BIT_EXPLORED = 1 << 0;
	private static final byte BIT_LOCKED = 1 << 1;
	
	public boolean explored = false;
	public boolean locked = false;
	
	public final IonBundle extra = new IonBundle();
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		final byte flags = in.readByte();
		in.readBundle(extra);
		
		explored = (flags & BIT_EXPLORED) != 0;
		locked = (flags & BIT_LOCKED) != 0;
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		byte flags = 0;
		if (explored) flags |= BIT_EXPLORED;
		if (locked) flags |= BIT_LOCKED;
		out.writeByte(flags);
		
		
		out.writeBundle(extra);
	}
	
	
	@Override
	public short getIonMark()
	{
		throw new YouFuckedUpException("TileData is not to be read from ION using mark.");
	}
	
}

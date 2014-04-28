package mightypork.rogue.world.tile.tiles;


import java.io.IOException;

import mightypork.rogue.world.tile.TileRenderer;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


public class LockedDoorTile extends DoorTile {
	
	public boolean locked = true;
	
	
	public LockedDoorTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		locked = in.readBoolean();
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		out.writeBoolean(locked);
	}
}

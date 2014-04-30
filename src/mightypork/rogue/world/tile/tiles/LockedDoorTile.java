package mightypork.rogue.world.tile.tiles;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;


public class LockedDoorTile extends DoorTile {
	
	public boolean locked = true;
	
	
	public LockedDoorTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return !locked;
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

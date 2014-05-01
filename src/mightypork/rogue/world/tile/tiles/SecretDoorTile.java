package mightypork.rogue.world.tile.tiles;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.timing.TimedTask;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class SecretDoorTile extends LockedDoorTile {
	
	private int clicks = 2 + rand.nextInt(2);
	
	
	public SecretDoorTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public boolean onClick()
	{
		if (!locked) return false;
		
		if (clicks > 0) clicks--;
		
		if (clicks == 0) locked = false;
		
		return true;
	}
	
	
	@Override
	public Color getMapColor()
	{
		if (locked) return TileType.WALL.getMapColor();
		return super.getMapColor();
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		out.writeIntByte(clicks);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		clicks = in.readIntByte();
	}
}

package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.util.math.color.Color;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class SecretDoorTile extends LockedDoorTile {
	
	public SecretDoorTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public boolean onClick()
	{
		if (!locked) return false;
		
		locked = false;
		
		return true;
	}
	
	
	@Override
	public Color getMapColor()
	{
		if (locked) return TileType.WALL.getMapColor();
		return super.getMapColor();
	}
}

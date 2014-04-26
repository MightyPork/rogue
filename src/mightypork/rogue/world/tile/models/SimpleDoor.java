package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.renderers.DoorRenderer;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.PAL16;


public class SimpleDoor extends AbstractTile {
	
	public SimpleDoor(int id)
	{
		super(id);
		setRenderer(new DoorRenderer("tile.door.closed", "tile.door.open"));
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return !isLocked(tile);
	}
	
	
	protected boolean isLocked(Tile tile)
	{
		return false;
	}
	
	
	@Override
	public boolean isDoor()
	{
		return true;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return true;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return false;
	}
	
	
	@Override
	public Color getMapColor(Tile tile)
	{
		return PAL16.NEWPOOP;
	}
}

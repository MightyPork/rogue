package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.renderers.DoorRenderer;


/**
 * Template for floor tiles with no metadata
 * 
 * @author MightyPork
 */
public class SimpleDoor extends Wall {
	
	public SimpleDoor(int id)
	{
		super(id);
		setRenderer(new DoorRenderer("tile.door.closed", "tile.door.open"));
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return true;
	}
	
	
	@Override
	public boolean isDoor()
	{
		return true;
	}
}

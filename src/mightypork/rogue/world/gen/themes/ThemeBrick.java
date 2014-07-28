package mightypork.rogue.world.gen.themes;


import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;


// basic dungeon theme
public class ThemeBrick implements MapTheme {

	@Override
	public TileModel wall()
	{
		return Tiles.BRICK_WALL;
	}


	@Override
	public TileModel floor()
	{
		return Tiles.BRICK_FLOOR;
	}


	@Override
	public TileModel door()
	{
		return Tiles.BRICK_DOOR;
	}


	@Override
	public TileModel passage()
	{
		return Tiles.BRICK_PASSAGE;
	}


	@Override
	public TileModel secretDoor()
	{
		return Tiles.BRICK_HIDDEN_DOOR;
	}


	@Override
	public TileModel entrance()
	{
		return Tiles.BRICK_ENTRANCE;
	}


	@Override
	public TileModel exit()
	{
		return Tiles.BRICK_EXIT;
	}


	@Override
	public TileModel chest()
	{
		return Tiles.BRICK_CHEST;
	}
}

package mightypork.rogue.world.gen.themes;


import mightypork.rogue.world.gen.Theme;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;


// basic dungeon theme
public class ThemeDungeon implements Theme {
	
	@Override
	public TileModel wall()
	{
		return Tiles.WALL_BRICK;
	}
	
	
	@Override
	public TileModel floor()
	{
		return Tiles.FLOOR_DARK;
	}
	
	
	@Override
	public TileModel door()
	{
		return Tiles.DOOR;
	}
	
}

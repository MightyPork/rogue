package mightypork.rogue.world.gen.themes;


import mightypork.rogue.world.gen.Theme;
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.models.TileModel;


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
		return floor(); // TODO
	}
	
}

package mightypork.rogue.world.tile.tiles;

import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


/**
 * Collapsed wall that's walk-through
 * 
 * @author MightyPork
 */
public class WallPassageTile extends SolidTile {

	public WallPassageTile(TileModel model, TileRenderer renderer) {
		super(model, renderer);
	}

	@Override
	public TileType getType()
	{
		return TileType.PASSAGE;
	}
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
}

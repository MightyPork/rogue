package mightypork.rogue.world.tile.tiles.brick;

import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.tiles.TileBaseDoor;


public class TileBrickDoor extends TileBaseDoor {

	public TileBrickDoor(TileModel model)
	{
		//@formatter:off
		super(
			model,
			Res.sheet("tile.brick.door.locked"),
			Res.sheet("tile.brick.door.closed"),
			Res.sheet("tile.brick.door.open")
		);
		//@formatter:on
	}
	
}

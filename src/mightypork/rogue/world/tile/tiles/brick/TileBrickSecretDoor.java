package mightypork.rogue.world.tile.tiles.brick;

import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.tiles.TileBaseDoor;
import mightypork.rogue.world.tile.tiles.TileBaseSecretDoor;


public class TileBrickSecretDoor extends TileBaseSecretDoor {

	public TileBrickSecretDoor(TileModel model)
	{
		//@formatter:off
		super(
			model,
			Res.sheet("tile.brick.door.secret"),
			Res.sheet("tile.brick.door.closed"),
			Res.sheet("tile.brick.door.open")
		);
		//@formatter:on
		
		locked = true; // hide it
	}
}
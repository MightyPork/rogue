package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseSecretDoor;


public class TileBrickSecretDoor extends TileBaseSecretDoor {

	public TileBrickSecretDoor(TileModel model)
	{
		//@formatter:off
		super(
				model,
				Res.txSheet("tile.brick.door.secret"),
				Res.txSheet("tile.brick.door.closed"),
				Res.txSheet("tile.brick.door.open")
				);
		//@formatter:on

		locked = true; // hide it
	}
}

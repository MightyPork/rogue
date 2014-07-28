package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseDoor;


public class TileBrickDoor extends TileBaseDoor {

	public TileBrickDoor(TileModel model)
	{
		//@formatter:off
		super(
				model,
				Res.txSheet("tile.brick.door.closed"), // LOCKED
				Res.txSheet("tile.brick.door.closed"),
				Res.txSheet("tile.brick.door.open")
				);
		//@formatter:on
	}

}

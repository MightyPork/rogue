package mightypork.rogue.world.tile.impl.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseSecretDoor;


public class TileBrickSecretDoor extends TileBaseSecretDoor {
	
	public TileBrickSecretDoor(TileModel model)
	{
		//@formatter:off
		super(
			model,
			Res.getTxSheet("tile.brick.door.secret"),
			Res.getTxSheet("tile.brick.door.closed"),
			Res.getTxSheet("tile.brick.door.open")
		);
		//@formatter:on
		
		locked = true; // hide it
	}
}

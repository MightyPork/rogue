package mightypork.rogue.world.tile.impl.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseDoor;


public class TileBrickDoor extends TileBaseDoor {
	
	public TileBrickDoor(TileModel model)
	{
		//@formatter:off
		super(
			model,
			Res.txs("tile.brick.door.closed"), // LOCKED 
			Res.txs("tile.brick.door.closed"),
			Res.txs("tile.brick.door.open")
		);
		//@formatter:on
	}
	
}
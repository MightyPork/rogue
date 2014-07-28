package mightypork.rogue.world.gen;


import mightypork.rogue.world.tile.TileModel;


/**
 * Map theme to use for building
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface MapTheme {

	TileModel wall();


	TileModel floor();


	TileModel door();


	TileModel passage();


	TileModel secretDoor();


	TileModel entrance();


	TileModel exit();


	TileModel chest();
}

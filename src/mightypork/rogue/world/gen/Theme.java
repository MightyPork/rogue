package mightypork.rogue.world.gen;


import mightypork.rogue.world.tile.TileModel;


/**
 * Map theme to use for building
 * 
 * @author MightyPork
 */
public interface Theme {
	
	TileModel wall();
	
	
	TileModel floor();
	
	
	TileModel door();
}

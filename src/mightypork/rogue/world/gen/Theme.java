package mightypork.rogue.world.gen;

import mightypork.rogue.world.tile.models.TileModel;

// map theme
public interface Theme {
	
	TileModel wall();
	
	
	TileModel floor();


	TileModel door();	
}
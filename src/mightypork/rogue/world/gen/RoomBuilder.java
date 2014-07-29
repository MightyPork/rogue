package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.utils.math.algo.Coord;


/**
 * Room model
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface RoomBuilder {
	
	RoomEntry buildRoom(ScratchMap map, MapTheme theme, Random rand, Coord center) throws WorldGenError;
}

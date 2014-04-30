package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;


/**
 * Room model
 * 
 * @author MightyPork
 */
public interface RoomBuilder {
	
	RoomDesc buildToFit(ScratchMap map, MapTheme theme, Random rand, Coord center);
}

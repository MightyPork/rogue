package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.rogue.world.Coord;


/**
 * Room model
 * 
 * @author MightyPork
 */
public interface RoomBuilder {
	
	RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center);
}

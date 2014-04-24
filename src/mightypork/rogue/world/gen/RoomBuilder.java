package mightypork.rogue.world.gen;

import java.util.List;
import java.util.Random;

import mightypork.rogue.world.tile.Tile;

// room builder interface
public interface RoomBuilder {	
	
	RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center);
}
package mightypork.rogue.world.gen.rooms;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.rogue.world.gen.Coord;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.Theme;
import mightypork.rogue.world.gen.ScratchMap;


public class IntersectionRoom extends SquareRoom {
	
	@Override
	protected int getMinHalfSide()
	{
		return 1;
	}
	
	
	@Override
	protected int getMaxHalfSide()
	{
		return 1;
	}
	
	
	protected int[] getDoorTypes()
	{
		//@formatter:off
		return new int[] {
				// dead ends
				0b1000,0b0100,0b0010,0b0001,
				
				// corridor pieces
				0b0011, 0b0101, 0b0110, 0b1010, 0b1100, 0b1001,
				
				// crossings
				0b0111, 0b1101, 0b1011, 0b1110, 0b1111,

				// repeat to get more
				0b0111, 0b1101, 0b1011, 0b1110, 0b1111,
		};
		//@formatter:on
	}
}

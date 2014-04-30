package mightypork.gamecore.util.math.algo.floodfill;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;


public interface FillContext {
	
	boolean canEnter(Coord pos);
	
	
	boolean canSpreadFrom(Coord pos);
	
	
	Step[] getSpreadSides();
	
	
	/**
	 * Get the max distance filled form start point. Use -1 for unlimited range.
	 * 
	 * @return max distance
	 */
	double getMaxDistance();
	
	
	/**
	 * @return true if start should be spread no matter what
	 */
	boolean forceSpreadStart();
}

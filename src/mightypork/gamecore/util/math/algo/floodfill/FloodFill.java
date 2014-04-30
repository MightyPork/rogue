package mightypork.gamecore.util.math.algo.floodfill;


import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;


public class FloodFill {
	
	/**
	 * Fill an area
	 * 
	 * @param start start point
	 * @param context filling context
	 * @param foundNodes collection to put filled coords in
	 * @return true if fill was successful; false if max range was reached.
	 */
	public static final boolean fill(Coord start, FillContext context, Collection<Coord> foundNodes)
	{
		final Queue<Coord> activeNodes = new LinkedList<>();
		
		final double maxDist = context.getMaxDistance();
		
		activeNodes.add(start);
		
		final Step[] sides = context.getSpreadSides();
		boolean forceSpreadNext = context.forceSpreadStart();
		
		boolean limitReached = false;
		
		while (!activeNodes.isEmpty()) {
			final Coord current = activeNodes.poll();
			foundNodes.add(current);
			
			if (!context.canSpreadFrom(current) && !forceSpreadNext) continue;
			
			forceSpreadNext = false;
			
			for (final Step spr : sides) {
				final Coord next = current.add(spr);
				if (activeNodes.contains(next) || foundNodes.contains(next)) continue;
				
				if (next.dist(start) > maxDist) {
					limitReached = true;
					continue;
				}
				
				if (context.canEnter(next)) {
					activeNodes.add(next);
				} else {
					foundNodes.add(next);
				}
			}
		}
		
		return !limitReached;
	}
}

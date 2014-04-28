package mightypork.rogue.world.pathfinding;


import java.util.*;

import mightypork.rogue.world.Coord;


public class FloodFill {
	
	private static final Coord[] spread = { Coord.make(0, -1), Coord.make(0, 1), Coord.make(1, 0), Coord.make(-1, 0) };
	
	
	public static final Collection<Coord> fill(Coord start, FillContext context)
	{
		Set<Coord> filled = new HashSet<>();
		Stack<Coord> active = new Stack<>();
		
		int maxDist = context.getMaxDistance();
		
		active.push(start);
		
		while (!active.isEmpty()) {
			Coord current = active.pop();
			
			filled.add(current);
			
			for (Coord spr : spread) {
				Coord next = current.add(spr);
				
				if (next.dist(start) > maxDist) continue;
				
				if (context.canSpread(next)) {
					active.push(next);
				}
			}
		}
		
		return filled;
	}
}

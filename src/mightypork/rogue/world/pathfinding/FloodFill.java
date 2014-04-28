package mightypork.rogue.world.pathfinding;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import mightypork.rogue.world.Coord;


public class FloodFill {	
	
	public static final Collection<Coord> fill(Coord start, FillContext context)
	{
		Set<Coord> filled = new HashSet<>();
		Stack<Coord> active = new Stack<>();
		
		double maxDist = context.getMaxDistance();
		
		active.push(start);
		
		Coord[] sides = context.getSpreadSides();
		boolean first = true;
		
		while (!active.isEmpty()) {			
			Coord current = active.pop();
			filled.add(current);
			
			if(!context.canSpreadFrom(current) && !first) continue;
			
			first = false;
			
			
			for (Coord spr : sides) {
				Coord next = current.add(spr);
				if(active.contains(next) || filled.contains(next)) continue;
				
				if (next.dist(start) > maxDist) continue;
				
				if (context.canEnter(next)) {
					active.push(next);
				} else {
					filled.add(next);
				}
			}
		}
		
		return filled;
	}
}

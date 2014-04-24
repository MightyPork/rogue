package mightypork.rogue.world.pathfinding;


import mightypork.rogue.world.Coord;


public class DiagonalHeuristic extends Heuristic {
	
	@Override
	public double getCost(Coord pos, Coord target)
	{
		return Math.sqrt(Math.pow(pos.x - target.x, 2) + Math.pow(pos.y - target.y, 2));
	}
}

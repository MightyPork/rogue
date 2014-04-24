package mightypork.rogue.world.pathfinding;


import mightypork.rogue.world.Coord;


public class ManhattanHeuristic extends Heuristic {
	
	@Override
	public double getCost(Coord pos, Coord target)
	{
		return Math.abs(target.x - pos.x) + Math.abs(target.y - pos.y);
	}
}

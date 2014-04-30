package mightypork.gamecore.util.math.algo.pathfinding.heuristics;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;


public class ManhattanHeuristic extends Heuristic {
	
	@Override
	public double getCost(Coord pos, Coord target)
	{
		return Math.abs(target.x - pos.x) + Math.abs(target.y - pos.y);
	}
}

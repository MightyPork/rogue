package mightypork.gamecore.util.math.algo.pathfinding.heuristics;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;


public class DiagonalHeuristic extends Heuristic {
	
	@Override
	public double getCost(Coord pos, Coord target)
	{
		return Math.sqrt(Math.pow(pos.x - target.x, 2) + Math.pow(pos.y - target.y, 2));
	}
}

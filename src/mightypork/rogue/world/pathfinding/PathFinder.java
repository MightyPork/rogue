package mightypork.rogue.world.pathfinding;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.PathStep;


/**
 * A* pathfinder
 * 
 * @author MightyPork
 */
public class PathFinder {
	
	private static final FComparator F_COMPARATOR = new FComparator();
	public static final Heuristic CORNER_HEURISTIC = new ManhattanHeuristic();
	public static final Heuristic DIAGONAL_HEURISTIC = new DiagonalHeuristic();
	
	private final PathCostProvider costProvider;
	private final Heuristic heuristic;
	
	
	public PathFinder(PathCostProvider costProvider, Heuristic heuristic)
	{
		this.costProvider = costProvider;
		this.heuristic = heuristic;
	}
	
	
	public List<PathStep> findPathRelative(Coord start, Coord end)
	{
		final List<Coord> path = findPath(start, end);
		if (path == null) return null;
		
		final List<PathStep> out = new ArrayList<>();
		
		final Coord current = start;
		for (final Coord c : path) {
			if (c.equals(current)) continue;
			out.add(PathStep.make(c.x - current.x, c.y - current.y));
			current.x = c.x;
			current.y = c.y;
		}
		
		return out;
	}
	
	
	public List<Coord> findPath(Coord start, Coord end)
	{
		final LinkedList<Node> open = new LinkedList<>();
		final LinkedList<Node> closed = new LinkedList<>();
		
		// add first node
		{
			final Node n = new Node(start);
			n.h_cost = (int) (heuristic.getCost(start, end) * costProvider.getMinCost());
			n.g_cost = 0;
			open.add(n);
		}
		
		//@formatter:off
		final Coord[] walkDirs = {
				Coord.make(0, -1),
				Coord.make(0, 1),
				Coord.make(-1, 0),
				Coord.make(1, 0)
		};
		//@formatter:on
		
		Node current = null;
		
		while (true) {
			current = open.poll();
			
			if (current == null) {
				break;
			}
			
			closed.add(current);
			
			if (current.pos.equals(end)) {
				break;
			}
			
			for (final Coord go : walkDirs) {
				
				final Coord c = current.pos.add(go);
				if (!costProvider.isAccessible(c)) continue;
				final Node a = new Node(c);
				a.g_cost = current.g_cost + costProvider.getCost(c, a.pos);
				a.h_cost = (int) (heuristic.getCost(a.pos, end) * costProvider.getMinCost());
				a.parent = current;
				
				if (costProvider.isAccessible(a.pos)) {
					if (!closed.contains(a)) {
						
						if (open.contains(a)) {
							
							boolean needSort = false;
							
							// find where it is
							for (final Node n : open) {
								if (n.pos.equals(a.pos)) { // found it
									if (n.g_cost > a.g_cost) {
										n.parent = current;
										n.g_cost = a.g_cost;
										needSort = true;
									}
									break;
								}
							}
							
							if (needSort) Collections.sort(open, F_COMPARATOR);
							
						} else {
							open.add(a);
						}
					}
				}
			}
			
		}
		
		if (current == null) {
			return null; // no path found
		}
		
		final LinkedList<Coord> path = new LinkedList<>();
		
		// extract path elements
		while (current != null) {
			path.addFirst(current.pos);
			current = current.parent;
		}
		
		return path;
	}
	
	private static class Node {
		
		Coord pos;
		int g_cost; // to get there
		int h_cost; // to target
		Node parent;
		
		
		public Node(Coord pos)
		{
			this.pos = pos;
		}
		
		
		int fCost()
		{
			return g_cost + h_cost;
		}
		
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((pos == null) ? 0 : pos.hashCode());
			return result;
		}
		
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof Node)) return false;
			final Node other = (Node) obj;
			if (pos == null) {
				if (other.pos != null) return false;
			} else if (!pos.equals(other.pos)) return false;
			return true;
		}
		
		
		@Override
		public String toString()
		{
			return "N " + pos + ", G =" + g_cost + ", H = " + h_cost;
		}
	}
	
	private static class FComparator implements Comparator<Node> {
		
		@Override
		public int compare(Node n1, Node n2)
		{
			return n1.fCost() - n2.fCost();
		}
	}
}

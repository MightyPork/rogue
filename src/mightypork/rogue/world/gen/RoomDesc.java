package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.List;


// room info
public class RoomDesc {
	
	final List<Coord> doors = new ArrayList<>();
	final Coord min;
	final Coord max;
	
	
	public RoomDesc(Coord min, Coord max, List<Coord> doors)
	{
		super();
		this.min = min;
		this.max = max;
		
		this.doors.addAll(doors);
	}
	
	
	public boolean intersectsWith(Coord amin, Coord amax)
	{
		int tw = max.x - min.x;
		int th = max.y - min.y;
		int rw = amax.x - amin.x;
		int rh = amax.y - amin.y;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		final int tx = min.x;
		final int ty = min.y;
		final int rx = amin.x;
		final int ry = amin.y;
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		return ((rw <= rx || rw >= tx) && (rh <= ry || rh >= ty) && (tw <= tx || tw >= rx) && (th <= ty || th >= ry));
	}
	
	
	@Override
	public String toString()
	{
		return "Room [" + min.x + "," + min.y + " .. " + max.x + "," + max.y + "]";
	}
}

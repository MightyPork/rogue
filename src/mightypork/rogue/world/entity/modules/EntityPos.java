package mightypork.rogue.world.entity.modules;


import java.io.IOException;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.entity.PathStep;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonBundled;
import mightypork.util.math.Easing;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.VectConst;
import mightypork.util.math.constraints.vect.mutable.VectAnimated;
import mightypork.util.timing.Updateable;


/**
 * Entity position
 * 
 * @author MightyPork
 */
class EntityPos implements IonBundled, Updateable {
	
	private final Coord coord = new Coord(0, 0);
	private final VectAnimated walkOffset = new VectAnimated(Vect.ZERO, Easing.LINEAR);
	
	
	public EntityPos(Coord pos)
	{
		this.coord.setTo(pos);
	}
	
	
	public EntityPos(int x, int y)
	{
		this.coord.setTo(x, y);
	}
	
	
	public EntityPos()
	{
	}
	
	
	public double getProgress()
	{
		return walkOffset.getProgress();
	}
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		in.loadBundled("pos", coord);
		walkOffset.reset();
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.putBundled("pos", coord);
	}
	
	
	public int x()
	{
		return coord.x;
	}
	
	
	public int y()
	{
		return coord.y;
	}
	
	
	public double visualX()
	{
		return coord.x + walkOffset.x();
	}
	
	
	public double visualY()
	{
		return coord.y + walkOffset.y();
	}
	
	
	public double visualXOffset()
	{
		return walkOffset.x();
	}
	
	
	public double visualYOffset()
	{
		return walkOffset.y();
	}
	
	
	public void setTo(int x, int y)
	{
		coord.setTo(x, y);
		walkOffset.reset();
	}
	
	
	public void setTo(EntityPos pos)
	{
		
		setTo(pos.getCoord());
		
	}
	
	
	public void setTo(Coord c)
	{
		coord.setTo(c);
		walkOffset.reset();
	}
	
	
	@Override
	public String toString()
	{
		return "EntityPos{" + coord + "}";
	}
	
	
	public void walk(PathStep step, double secs)
	{
		setTo(coord.x + step.x, coord.y + step.y);
		walkOffset.setTo(-step.x, -step.y);
		walkOffset.animate(0, 0, 0, secs);
	}
	
	
	@Override
	public void update(double delta)
	{
		walkOffset.update(delta);
	}
	
	
	public boolean isFinished()
	{
		return walkOffset.isFinished();
	}
	
	
	public Coord getCoord()
	{
		return coord;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof EntityPos)) return false;
		final EntityPos other = (EntityPos) obj;
		if (coord == null) {
			if (other.coord != null) return false;
		} else if (!coord.equals(other.coord)) return false;
		return true;
	}
	
	
	public VectConst getVisualPos()
	{
		return Vect.make(walkOffset.x() + coord.x, walkOffset.y() + coord.y);
	}
}

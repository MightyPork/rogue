package mightypork.gamecore.util.math.algo;


import java.io.IOException;

import mightypork.gamecore.util.annot.FactoryMethod;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.VectConst;
import mightypork.ion.IonBundle;
import mightypork.ion.IonInput;
import mightypork.ion.IonObjBinary;
import mightypork.ion.IonObjBundled;
import mightypork.ion.IonOutput;


/**
 * Very simple integer coordinate
 * 
 * @author Ondřej Hruška
 */
public class Coord implements IonObjBinary, IonObjBundled {
	
	public static final int ION_MARK = 255;
	
	public int x;
	public int y;
	
	
	@FactoryMethod
	public static Coord make(int x, int y)
	{
		return new Coord(x, y);
	}
	
	
	@FactoryMethod
	public static Coord make(Coord other)
	{
		return new Coord(other);
	}
	
	
	@FactoryMethod
	public static Coord zero()
	{
		return make(0, 0);
	}
	
	
	public Coord()
	{
		// for ion
	}
	
	
	public Coord(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public Coord(Coord other)
	{
		this.x = other.x;
		this.y = other.y;
	}
	
	
	public Coord add(int addX, int addY)
	{
		return new Coord(x + addX, y + addY);
	}
	
	
	/**
	 * Add other coord in a copy
	 * 
	 * @param added
	 * @return changed copy
	 */
	public Coord add(Coord added)
	{
		return add(added.x, added.y);
	}
	
	
	public Coord add(Move added)
	{
		return add(added.x(), added.y());
	}
	
	
	public Coord copy()
	{
		return make(this);
	}
	
	
	public void setTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public void setTo(Coord pos)
	{
		setTo(pos.x, pos.y);
	}
	
	
	/**
	 * Check if coord is in a range (inclusive)
	 */
	public boolean isInRange(int x0, int y0, int x1, int y1)
	{
		return !(x < x0 || x > x1 || y < y0 || y > y1);
	}
	
	
	public double dist(Coord coord)
	{
		return Calc.dist(x, y, coord.x, coord.y);
	}
	
	
	public VectConst toVect()
	{
		return Vect.make(x, y);
	}
	
	
	@Override
	public String toString()
	{
		return "Coord(" + x + "," + y + ")";
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Coord)) return false;
		final Coord other = (Coord) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		x = in.readInt();
		y = in.readInt();
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeInt(x);
		out.writeInt(y);
	}
	
	
	public static Coord fromVect(Vect vect)
	{
		return make((int) Math.floor(vect.x()), (int) Math.floor(vect.y()));
	}
	
	
	@Override
	public void load(IonBundle in)
	{
		x = in.get("x", x);
		y = in.get("y", y);
	}
	
	
	@Override
	public void save(IonBundle out)
	{
		out.put("x", x);
		out.put("y", y);
	}
}

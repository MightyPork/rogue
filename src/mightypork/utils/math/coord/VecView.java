package mightypork.utils.math.coord;


import mightypork.gamecore.control.interf.DefaultImpl;
import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Read-only coordinate.
 * 
 * @author MightyPork
 */
public abstract class VecView extends VecMath<VecView> {
	
	/**
	 * Get a zero (0,0,0) constant
	 * 
	 * @return new constant vec
	 */
	public static VecView zero()
	{
		return ZERO.view();
	}
	
	
	/**
	 * Get a one (1,1,1) constant
	 * 
	 * @return one constant
	 */
	public static VecView one()
	{
		return ONE.view();
	}
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param x X value
	 * @param y Y value
	 * @return new constant vec
	 */
	public static VecView make(double x, double y)
	{
		return new ConstVec(x, y);
	}
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 * @return new constant vector
	 */
	public static VecView make(double x, double y, double z)
	{
		return new ConstVec(x, y, z);
	}
	
	
	/**
	 * Make a view at number constraints, reflecting their future changes.
	 * 
	 * @param x X value
	 * @param y Y value
	 * @return view at the values
	 */
	public static VecView make(NumberConstraint x, NumberConstraint y)
	{
		return new NumConstrVec(x, y);
	}
	
	
	/**
	 * Make a view at number constraints, reflecting their future changes.
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 * @return view at the values
	 */
	public static VecView make(NumberConstraint x, NumberConstraint y, NumberConstraint z)
	{
		return new NumConstrVec(x, y, z);
	}
	
	// synth views
	private VecView view_round;
	private VecView view_floor;
	private VecView view_ceil;
	private VecView view_neg;
	private VecView view_half;
	
	
	@Override
	public VecView result(double x, double y, double z)
	{
		return new ConstVec(x, y, z);
	}
	
	
	@Override
	@Deprecated
	public VecView view()
	{
		return this; // already not mutable
	}
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	@DefaultImpl
	public double z()
	{
		return 0; // implemented for ease with 2D anonymous subtypes
	}
	
	
	@Override
	public VecView round()
	{
		// lazy init
		if (view_round == null) view_round = new Synths.Round(this);
		
		return view_round;
	}
	
	
	@Override
	public VecView floor()
	{
		// lazy init
		if (view_floor == null) view_floor = new Synths.Floor(this);
		
		return view_floor;
	}
	
	
	@Override
	public VecView ceil()
	{
		// lazy init
		if (view_ceil == null) view_ceil = new Synths.Ceil(this);
		
		return view_ceil;
	}
	
	
	@Override
	public VecView half()
	{
		// lazy init
		if (view_half == null) view_half = new Synths.Half(this);
		
		return view_half;
	}
	
	
	@Override
	public VecView neg()
	{
		// lazy init
		if (view_neg == null) view_neg = new Synths.Neg(this);
		
		return view_neg;
	}
}

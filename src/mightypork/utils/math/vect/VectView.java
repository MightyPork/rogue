package mightypork.utils.math.vect;


import mightypork.gamecore.control.interf.DefaultImpl;
import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Read-only coordinate.
 * 
 * @author MightyPork
 */
public abstract class VectView extends VectMath<VectVal> { // returns constant value on edit
	
	/**
	 * Make a proxy view at a vector.
	 * 
	 * @param observed vector to observe
	 * @return view
	 */
	public static VectView make(Vect observed)
	{
		return observed.view();
	}
	
	
	/**
	 * Make a view at number constraints, reflecting their future changes.
	 * 
	 * @param xc X value
	 * @param yc Y value
	 * @return view at the values
	 */
	public static VectView make(NumberConstraint xc, NumberConstraint yc)
	{
		return new NumConstrVect(xc, yc);
	}
	
	
	/**
	 * Make a view at number constraints, reflecting their future changes.
	 * 
	 * @param xc X value
	 * @param yc Y value
	 * @param zc Z value
	 * @return view at the values
	 */
	public static VectView make(NumberConstraint xc, NumberConstraint yc, NumberConstraint zc)
	{
		return new NumConstrVect(xc, yc, zc);
	}
	
	
	@Override
	public VectVal result(double x, double y, double z)
	{
		return VectVal.make(x, y, z);
	}
	
	
	/**
	 * @deprecated VecView is not mutable, making a proxy has no effect.
	 */
	@Override
	@Deprecated
	public VectView view()
	{
		return this; // already not mutable
	}
	
	
	@Override
	@DefaultImpl
	public double z()
	{
		return 0; // implemented for ease with 2D anonymous subtypes
	}

}

package mightypork.utils.math.vect;


import mightypork.gamecore.control.interf.DefaultImpl;
import mightypork.utils.math.constraints.NumBound;


/**
 * Read-only coordinate, whose values cannot be changed directly. To keep
 * current state, use the value() method.
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
	public static VectView make(NumBound xc, NumBound yc)
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
	public static VectView make(NumBound xc, NumBound yc, NumBound zc)
	{
		return new NumConstrVect(xc, yc, zc);
	}
	
	
	@Override
	public VectVal result(double x, double y, double z)
	{
		return VectVal.make(x, y, z);
	}
	
	
	@Override
	public VectView view()
	{
		return this; // already a view
	}
	
	
	@Override
	@DefaultImpl
	public double z()
	{
		return 0; // implemented for ease with 2D anonymous subtypes
	}
	
}

package mightypork.utils.math.vect;


import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.num.Num;


/**
 * Read-only immutable dynamic view of a coordinate. When the values change,
 * it'll be propagated in all derived coords.<br>
 * To take a static copy, use the copy() method.
 * 
 * @author MightyPork
 */
public abstract class VectView extends VectMathDynamic { // returns constant value on edit

	@SuppressWarnings("hiding")
	public static final VectView ZERO = new VectVal(0, 0, 0).view();
	@SuppressWarnings("hiding")
	public static final VectView ONE = new VectVal(1, 1, 1).view();
	
	
	/**
	 * Make a proxy view at a vector.
	 * 
	 * @param observed vector to observe
	 * @return view
	 */
	@FactoryMethod
	public static VectView make(Vect observed)
	{
		return observed.view(); // let the vect handle it
	}
	
	
	/**
	 * Make a view at number constraints, reflecting their future changes.
	 * 
	 * @param xc X value
	 * @param yc Y value
	 * @return view at the values
	 */
	@FactoryMethod
	public static VectView make(Num xc, Num yc)
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
	@FactoryMethod
	public static VectView make(Num xc, Num yc, Num zc)
	{
		return new NumConstrVect(xc, yc, zc);
	}
	
	
	@Override
	@DefaultImpl
	public double z()
	{
		return 0; // implemented for ease with 2D anonymous subtypes
	}
	
	
	/**
	 * @deprectaed No point in taking view of a view.
	 */
	@Override
	@Deprecated
	public VectView view()
	{
		return super.view();
	}
	
}

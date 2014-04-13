package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.constraints.VectBound;


/**
 * The most basic Vec methods
 * 
 * @author MightyPork
 */
public interface Vect extends VectBound {
	
	public static final VectVal ZERO = new VectVal(0, 0, 0);
	public static final VectVal ONE = new VectVal(0, 0, 0);
	
	
	/**
	 * @return X coordinate
	 */
	double x();
	
	
	/**
	 * @return Y coordinate
	 */
	double y();
	
	
	/**
	 * @return Z coordinate
	 */
	double z();
	
	
	/**
	 * @return X rounded
	 */
	int xi();
	
	
	/**
	 * @return Y rounded
	 */
	int yi();
	
	
	/**
	 * @return Z rounded
	 */
	int zi();
	
	
	/**
	 * @return X constraint
	 */
	NumBound xc();
	
	
	/**
	 * @return Y constraint
	 */
	NumBound yc();
	
	
	/**
	 * @return Z constraint
	 */
	NumBound zc();
	
	
	/**
	 * Get vector size
	 * 
	 * @return size
	 */
	double size();
	
	
	/**
	 * @return true if zero
	 */
	public boolean isZero();
	
	
	/**
	 * Get a view at current state, not propagating further changes.
	 * 
	 * @return a immutable copy
	 */
	VectVal copy();
	
	
	/**
	 * Get immutable proxy view at this vec
	 * 
	 * @return immutable view
	 */
	VectView view();
}

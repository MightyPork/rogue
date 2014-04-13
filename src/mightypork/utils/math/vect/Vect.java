package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.VectBound;


/**
 * The most basic Vec methods
 * 
 * @author MightyPork
 */
public interface Vect extends VectBound {
	
	Vect ZERO = new VectVal(0, 0, 0);
	Vect ONE = new VectVal(1, 1, 1);
	
	
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
	 * @return true if zero
	 */
	boolean isZero();
	
	
	/**
	 * Get a static immutable copy of the current state.
	 * 
	 * @return a immutable copy
	 */
	VectVal copy();
	
	
	/**
	 * Get dynamic immutable view at this rect
	 * 
	 * @return immutable view
	 */
	VectView view();
}

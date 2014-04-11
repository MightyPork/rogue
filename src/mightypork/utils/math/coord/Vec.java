package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;


public interface Vec {
	
	public static final VecView ZERO = new CoordValue(0, 0, 0);
	public static final VecView ONE = new CoordValue(1, 1, 1);
	
	
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
	 * @return X as float
	 */
	float xf();
	
	
	/**
	 * @return Y as float
	 */
	float yf();
	
	
	/**
	 * @return Z as float
	 */
	float zf();
	
	
	/**
	 * @return X constraint
	 */
	NumberConstraint xc();
	
	
	/**
	 * @return Y constraint
	 */
	NumberConstraint yc();
	
	
	/**
	 * @return Z constraint
	 */
	NumberConstraint zc();
	
	
	/**
	 * Get a new mutable variable holding the current state
	 * 
	 * @return a mutable copy
	 */
	MutableCoord copy();
	
	
	/**
	 * Get immutable view at this vec
	 * 
	 * @return immutable view
	 */
	CoordProxy view();
	
	
}

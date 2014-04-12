package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumberConstraint;
import mightypork.utils.math.constraints.VectConstraint;


/**
 * The most basic Vec methods
 * 
 * @author MightyPork
 */
public interface Vect extends VectConstraint {
	
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
	 * Get distance to other point
	 * 
	 * @param point other point
	 * @return distance
	 */
	double distTo(Vect point);
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	VectVal midTo(Vect point);
	
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return result
	 */
	VectVal vecTo(Vect point);
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	VectVal cross(Vect vec);
	
	
	/**
	 * Get dot product (scalar multiplication)
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	double dot(Vect vec);
	
	
	/**
	 * Get a view at current state, not propagating further changes.
	 * 
	 * @return a immutable copy
	 */
	VectVal value();
	
	
	/**
	 * Get immutable proxy view at this vec
	 * 
	 * @return immutable view
	 */
	VectView view();
	
	
	/**
	 * Get a mutable copy of current values.
	 * 
	 * @return mutable copy
	 */
	VectMutable mutable();
}
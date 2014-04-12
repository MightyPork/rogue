package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;
import mightypork.utils.math.constraints.VecConstraint;


/**
 * The most basic Vec methods
 * 
 * @author MightyPork
 */
public interface Vec extends VecConstraint {
	
	public static final VecView ZERO = new ConstVec(0, 0, 0);
	public static final VecView ONE = new ConstVec(0, 0, 0);
	
	
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
	@Override
	NumberConstraint xc();
	
	
	/**
	 * @return Y constraint
	 */
	@Override
	NumberConstraint yc();
	
	
	/**
	 * @return Z constraint
	 */
	@Override
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
	double distTo(Vec point);
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	VecView midTo(Vec point);
	
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return result
	 */
	public VecView vecTo(Vec point);
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	public VecView cross(Vec vec);
	
	
	/**
	 * Get dot product (scalar multiplication)
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	public double dot(Vec vec);
	
	
	/**
	 * Get a view at current state, not propagating further changes.
	 * 
	 * @return a immutable copy
	 */
	VecView value();
	
	
	/**
	 * Get immutable proxy view at this vec
	 * 
	 * @return immutable view
	 */
	VecView view();
	
	
	/**
	 * Get a mutable copy of current values.
	 * 
	 * @return mutable copy
	 */
	VecMutable mutable();
}

package mightypork.utils.math.coord;

import mightypork.utils.math.constraints.VecConstraint;


/**
 * 3D coordinate methods
 * 
 * @author MightyPork
 */
interface VecArith extends Vec {
	
	/**
	 * Set X coordinate (if immutable, in a copy).
	 * 
	 * @param x x coordinate
	 * @return result
	 */
	Vec setX(double x);
	
	
	/**
	 * Set Y coordinate (if immutable, in a copy).
	 * 
	 * @param y y coordinate
	 * @return result
	 */
	Vec setY(double y);
	
	
	/**
	 * Set Z coordinate (if immutable, in a copy).
	 * 
	 * @param z z coordinate
	 * @return result
	 */
	Vec setZ(double z);

	
	/**
	 * Get distance to other point
	 * 
	 * @param point other point
	 * @return distance
	 */
	double distTo(Vec point);
	
	
	/**
	 * Get dot product (scalar multiplication)
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	double dot(Vec vec);
	
	
	/**
	 * Get vector size
	 * 
	 * @return size
	 */
	double size();
	
	
	/**
	 * @return true if zero
	 */
	boolean isZero();
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return result
	 */
	Vec vecTo(Vec point);
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	Vec midTo(Vec point);
	
	
	/**
	 * Add a vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	Vec add(Vec vec);
	
	
	/**
	 * Add to each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	Vec add(double x, double y);
	
	
	/**
	 * Add to each component.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	Vec add(double x, double y, double z);
	
	
	/**
	 * Get copy divided by two
	 * 
	 * @return result
	 */
	Vec half();
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param d multiplier
	 * @return result
	 */
	Vec mul(double d);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param vec vector of multipliers
	 * @return result
	 */
	Vec mul(Vec vec);
	
	
	/**
	 * Multiply each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return result
	 */
	Vec mul(double x, double y);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return result
	 */
	Vec mul(double x, double y, double z);
	
	
	/**
	 * Round coordinates.
	 * 
	 * @return result
	 */
	Vec round();
	
	
	/**
	 * Round coordinates down.
	 * 
	 * @return result
	 */
	Vec floor();
	
	
	/**
	 * Round coordinates up.
	 * 
	 * @return result
	 */
	Vec ceil();
	
	
	/**
	 * Subtract vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	Vec sub(Vec vec);
	
	
	/**
	 * Subtract a 2D vector.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	VecArith sub(double x, double y);
	
	
	/**
	 * Subtract a 3D vector.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	Vec sub(double x, double y, double z);
	
	
	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return result
	 */
	Vec neg();
	
	
	/**
	 * Scale vector to given size.
	 * 
	 * @param size size we need
	 * @return result
	 */
	Vec norm(double size);
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	public Vec cross(Vec vec);
	
}

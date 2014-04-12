package mightypork.utils.math.coord;


/**
 * 3D coordinate methods
 * 
 * @author MightyPork
 */
interface VecMath<V> extends Vec {
	
	/**
	 * Set X coordinate (if immutable, in a copy).
	 * 
	 * @param x x coordinate
	 * @return result
	 */
	V setX(double x);
	
	
	/**
	 * Set Y coordinate (if immutable, in a copy).
	 * 
	 * @param y y coordinate
	 * @return result
	 */
	V setY(double y);
	
	
	/**
	 * Set Z coordinate (if immutable, in a copy).
	 * 
	 * @param z z coordinate
	 * @return result
	 */
	V setZ(double z);
	
	
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
	 * Get absolute value (positive)
	 * 
	 * @return result
	 */
	V abs();
	
	
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
	V vecTo(Vec point);
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	V midTo(Vec point);
	
	
	/**
	 * Add a vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	V add(Vec vec);
	
	
	/**
	 * Add to each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	V add(double x, double y);
	
	
	/**
	 * Add to each component.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	V add(double x, double y, double z);
	
	
	/**
	 * Get copy divided by two
	 * 
	 * @return result
	 */
	V half();
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param d multiplier
	 * @return result
	 */
	V mul(double d);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param vec vector of multipliers
	 * @return result
	 */
	V mul(Vec vec);
	
	
	/**
	 * Multiply each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return result
	 */
	V mul(double x, double y);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return result
	 */
	V mul(double x, double y, double z);
	
	
	/**
	 * Round coordinates.
	 * 
	 * @return result
	 */
	V round();
	
	
	/**
	 * Round coordinates down.
	 * 
	 * @return result
	 */
	V floor();
	
	
	/**
	 * Round coordinates up.
	 * 
	 * @return result
	 */
	V ceil();
	
	
	/**
	 * Subtract vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	V sub(Vec vec);
	
	
	/**
	 * Subtract a 2D vector.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	V sub(double x, double y);
	
	
	/**
	 * Subtract a 3D vector.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	V sub(double x, double y, double z);
	
	
	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return result
	 */
	V neg();
	
	
	/**
	 * Scale vector to given size.
	 * 
	 * @param size size we need
	 * @return result
	 */
	V norm(double size);
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	V cross(Vec vec);
	
}

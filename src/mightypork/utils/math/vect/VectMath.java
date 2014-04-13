package mightypork.utils.math.vect;


/**
 * Implementation of coordinate methods
 * 
 * @author MightyPork
 * @param <V> Return type of methods
 */
abstract class VectMath<V extends Vect> extends AbstractVect {
	
	/**
	 * <p>
	 * Some operation was performed and this result was obtained.
	 * </p>
	 * <p>
	 * It's now up to implementing class what to do - mutable ones can alter
	 * it's data values, immutable can return a new Vec.
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return the result Vec
	 */
	public abstract V result(double x, double y, double z);
	
	
	/**
	 * Set X coordinate (if immutable, in a copy).
	 * 
	 * @param x x coordinate
	 * @return result
	 */
	public V setX(double x)
	{
		return result(x, y(), z());
	}
	
	
	/**
	 * Set Y coordinate (if immutable, in a copy).
	 * 
	 * @param y y coordinate
	 * @return result
	 */
	public V setY(double y)
	{
		return result(x(), y, z());
	}
	
	
	/**
	 * Set Z coordinate (if immutable, in a copy).
	 * 
	 * @param z z coordinate
	 * @return result
	 */
	public V setZ(double z)
	{
		return result(x(), y(), z);
	}
	
	
	/**
	 * Get absolute value (positive)
	 * 
	 * @return result
	 */
	public V abs()
	{
		return result(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}
	
	
	/**
	 * Add a vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	public V add(Vect vec)
	{
		return add(vec.x(), vec.y(), vec.z());
	}
	
	
	/**
	 * Add to each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	public V add(double x, double y)
	{
		return add(x, y, 0);
	}
	
	
	/**
	 * Add to each component.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	public V add(double x, double y, double z)
	{
		return result(x() + x, y() + y, z() + z);
	}
	
	
	/**
	 * Get copy divided by two
	 * 
	 * @return result
	 */
	public V half()
	{
		return mul(0.5);
	}
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param d multiplier
	 * @return result
	 */
	public V mul(double d)
	{
		return mul(d, d, d);
	}
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param vec vector of multipliers
	 * @return result
	 */
	public V mul(Vect vec)
	{
		return mul(vec.x(), vec.y(), vec.z());
	}
	
	
	/**
	 * Multiply each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return result
	 */
	public V mul(double x, double y)
	{
		return mul(x, y, 1);
	}
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return result
	 */
	public V mul(double x, double y, double z)
	{
		return result(x() * x, y() * y, z() * z);
	}
	
	
	/**
	 * Round coordinates.
	 * 
	 * @return result
	 */
	public V round()
	{
		return result(Math.round(x()), Math.round(y()), Math.round(z()));
	}
	
	
	/**
	 * Round coordinates down.
	 * 
	 * @return result
	 */
	public V floor()
	{
		return result(Math.floor(x()), Math.floor(y()), Math.floor(z()));
	}
	
	
	/**
	 * Round coordinates up.
	 * 
	 * @return result
	 */
	public V ceil()
	{
		return result(Math.ceil(x()), Math.ceil(y()), Math.ceil(z()));
	}
	
	
	/**
	 * Subtract vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	public V sub(Vect vec)
	{
		return sub(vec.x(), vec.y(), vec.z());
	}
	
	
	/**
	 * Subtract a 2D vector.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	public V sub(double x, double y)
	{
		return sub(x, y, 0);
	}
	
	
	/**
	 * Subtract a 3D vector.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	public V sub(double x, double y, double z)
	{
		return result(x() - x, y() - y, z() - z);
	}
	
	
	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return result
	 */
	public V neg()
	{
		return result(-x(), -y(), -z());
	}
	
	
	/**
	 * Scale vector to given size.
	 * 
	 * @param size size we need
	 * @return result
	 */
	public V norm(double size)
	{
		if (isZero()) return result(x(), y(), z()); // can't norm zero vector
			
		final double k = size / size();
		
		return mul(k);
	}
	
	
	/**
	 * Get distance to other point
	 * 
	 * @param point other point
	 * @return distance
	 */
	public final double distTo(Vect point)
	{
		final double dx = x() - point.x();
		final double dy = y() - point.y();
		final double dz = z() - point.z();
		
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	public final VectVal midTo(Vect point)
	{
		final double dx = (point.x() - x()) * 0.5;
		final double dy = (point.y() - y()) * 0.5;
		final double dz = (point.z() - z()) * 0.5;
		
		return VectVal.make(dx, dy, dz);
	}
	
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return result
	 */
	public final VectVal vectTo(Vect point)
	{
		return VectVal.make(point.x() - x(), point.y() - y(), point.z() - z());
	}
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	public final VectVal cross(Vect vec)
	{
		//@formatter:off
		return VectVal.make(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x());
		//@formatter:on
	}
	
	
	/**
	 * Get dot product (scalar multiplication)
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	public final double dot(Vect vec)
	{
		return x() * vec.x() + y() * vec.y() + z() * vec.z();
	}
}

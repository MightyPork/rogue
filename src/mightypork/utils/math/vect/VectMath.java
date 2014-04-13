package mightypork.utils.math.vect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumVal;


/**
 * Vec operations
 * 
 * @author MightyPork
 * @param <V> return type of vector functions
 * @param <N> return type of numeric functions
 * @param <B> return type of boolean functions
 */
abstract class VectMath<V extends Vect, N> extends AbstractVect {
	
	/**
	 * @return X constraint
	 */
	public abstract Num xn();
	
	
	/**
	 * @return Y constraint
	 */
	public abstract Num yn();
	
	
	/**
	 * @return Z constraint
	 */
	public abstract Num zn();
	
	
	/**
	 * Set X coordinate (if immutable, in a copy).
	 * 
	 * @param x x coordinate
	 * @return result
	 */
	public VectView withX(double x)
	{
		return withX(NumVal.make(x));
	}
	
	
	/**
	 * Set Y coordinate (if immutable, in a copy).
	 * 
	 * @param y y coordinate
	 * @return result
	 */
	public VectView withY(double y)
	{
		return withY(NumVal.make(y));
	}
	
	
	/**
	 * Set Z coordinate (if immutable, in a copy).
	 * 
	 * @param z z coordinate
	 * @return result
	 */
	public VectView withZ(double z)
	{
		return withZ(NumVal.make(z));
	}
	
	
	public VectView withX(final Num x)
	{
		return new VectView() {
			
			final Vect t = VectMath.this;
			
			
			@Override
			public double x()
			{
				return x.value();
			}
			
			
			@Override
			public double y()
			{
				return t.z();
			}
			
			
			@Override
			public double z()
			{
				return t.z();
			}
		};
	}
	
	
	public VectView withY(final Num y)
	{
		return new VectView() {
			
			final Vect t = VectMath.this;
			
			
			@Override
			public double x()
			{
				return t.x();
			}
			
			
			@Override
			public double y()
			{
				return y.value();
			}
			
			
			@Override
			public double z()
			{
				return t.z();
			}
		};
	}
	
	
	public VectView withZ(final Num z)
	{
		return new VectView() {
			
			final Vect t = VectMath.this;
			
			
			@Override
			public double x()
			{
				return t.x();
			}
			
			
			@Override
			public double y()
			{
				return t.y();
			}
			
			
			@Override
			public double z()
			{
				return z.value();
			}
		};
	}
	
	
	/**
	 * Get absolute value (positive)
	 * 
	 * @return result
	 */
	public abstract V abs();
	
	
	/**
	 * Add a vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	public abstract V add(Vect vec);
	
	
	/**
	 * Add to each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	public abstract V add(double x, double y);
	
	
	/**
	 * Add to each component.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	public abstract V add(double x, double y, double z);
	
	
	public abstract V add(Num x, Num y);
	
	
	public abstract V add(final Num x, final Num y, final Num z);
	
	
	/**
	 * Get copy divided by two
	 * 
	 * @return result
	 */
	public abstract V half();
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param d multiplier
	 * @return result
	 */
	public abstract V mul(double d);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param vec vector of multipliers
	 * @return result
	 */
	public abstract V mul(Vect vec);
	
	
	/**
	 * Multiply each component.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return result
	 */
	public abstract V mul(double x, double y);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return result
	 */
	public abstract V mul(double x, double y, double z);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param d multiplier
	 * @return result
	 */
	public abstract V mul(final Num d);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return result
	 */
	public abstract V mul(final Num x, final Num y);
	
	
	/**
	 * Multiply each component.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return result
	 */
	public abstract V mul(final Num x, final Num y, final Num z);
	
	
	/**
	 * Round coordinates.
	 * 
	 * @return result
	 */
	public abstract V round();
	
	
	/**
	 * Round coordinates down.
	 * 
	 * @return result
	 */
	public abstract V floor();
	
	
	/**
	 * Round coordinates up.
	 * 
	 * @return result
	 */
	public abstract V ceil();
	
	
	/**
	 * Subtract vector.
	 * 
	 * @param vec offset
	 * @return result
	 */
	public abstract V sub(Vect vec);
	
	
	/**
	 * Subtract a 2D vector.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return result
	 */
	public abstract V sub(double x, double y);
	
	
	/**
	 * Subtract a 3D vector.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return result
	 */
	public abstract V sub(double x, double y, double z);
	
	
	public abstract V sub(Num x, Num y);
	
	
	public abstract V sub(final Num x, final Num y, final Num z);
	
	
	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return result
	 */
	public abstract V neg();
	
	
	/**
	 * Scale vector to given size.
	 * 
	 * @param size size we need
	 * @return result
	 */
	public abstract V norm(double size);
	
	
	/**
	 * Get distance to other point
	 * 
	 * @param point other point
	 * @return distance
	 */
	public abstract N distTo(Vect point);
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param point other point
	 * @return result
	 */
	public abstract V midTo(Vect point);
	
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return result
	 */
	public abstract V vectTo(Vect point);
	
	
	/**
	 * Get cross product (vector multiplication)
	 * 
	 * @param vec other vector
	 * @return result
	 */
	public abstract V cross(Vect vec);
	
	
	/**
	 * Get dot product (scalar multiplication)
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	public abstract N dot(Vect vec);
	
	
	/**
	 * Get vector size
	 * 
	 * @return size
	 */
	public abstract N size();
	
	
	@Override
	public boolean isZero()
	{
		return x() == 0 && y() == 0 && z() == 0;
	}
	
}

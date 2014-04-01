package mightypork.utils.math.coord;

import java.util.Random;

import mightypork.utils.math.Calc;


/**
 * Coordinate in 3D space, or a vector of three {@link Double}s<br>
 * 
 * @author MightyPork
 */
public class Coord {
	
	protected static Random rand = new Random();
	
	
	/**
	 * Get distance to other point
	 * 
	 * @param a point a
	 * @param b point b
	 * @return distance in units
	 */
	public static double dist(Coord a, Coord b)
	{
		return a.distTo(b);
	}
	
	/** X coordinate */
	public double x = 0;
	
	/** Y coordinate */
	public double y = 0;
	
	/** Z coordinate */
	public double z = 0;
	
	
	/**
	 * Create zero coord
	 */
	public Coord() {
	}
	
	
	/**
	 * Create coord as a copy of another
	 * 
	 * @param copied copied coord
	 */
	public Coord(Coord copied) {
		setTo(copied);
	}
	
	
	/**
	 * Create 2D coord
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coord(Number x, Number y) {
		setTo(x, y);
	}
	
	
	/**
	 * Create 3D coord
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public Coord(Number x, Number y, Number z) {
		setTo(x, y, z);
	}
	
	
	/**
	 * Add a vector, in a copy
	 * 
	 * @param vec offset
	 * @return changed copy
	 */
	public Coord add(Coord vec)
	{
		return copy().add_ip(vec);
	}
	
	
	/**
	 * Add a vector, in place
	 * 
	 * @param vec offset
	 * @return this
	 */
	public Coord add_ip(Coord vec)
	{
		return add_ip(vec.x, vec.y, vec.z);
	}
	
	
	/**
	 * Add to each component, in a copy.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return changed copy
	 */
	public Coord add(Number x, Number y)
	{
		return copy().add_ip(x, y);
	}
	
	
	/**
	 * Add to each component, in place.<br>
	 * Z is unchanged.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return this
	 */
	public Coord add_ip(Number x, Number y)
	{
		return add_ip(x, y, 0);
	}
	
	
	/**
	 * Add to each component, in a copy.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return changed copy
	 */
	public Coord add(Number x, Number y, Number z)
	{
		return copy().add_ip(x, y, z);
	}
	
	
	/**
	 * Add to each component, in place.
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return this
	 */
	public Coord add_ip(Number x, Number y, Number z)
	{
		this.x += x.doubleValue();
		this.y += y.doubleValue();
		this.z += z.doubleValue();
		return this;
	}
	
	
	/**
	 * Make a copy
	 * 
	 * @return a copy
	 */
	public Coord copy()
	{
		return new Coord(x, y, z);
	}
	
	
	/**
	 * Get distance to other point
	 * 
	 * @param point other point
	 * @return distance in units
	 */
	public double distTo(Coord point)
	{
		return Math.sqrt((point.x - x) * (point.x - x) + (point.y - y) * (point.y - y) + (point.z - z) * (point.z - z));
	}
	
	
	/**
	 * Check if this rectangle in inside a rectangular zone
	 * 
	 * @param rect checked rect.
	 * @return is inside
	 */
	public boolean isInRect(Rect rect)
	{
		return isInRect(rect.min, rect.max);
	}
	
	
	/**
	 * Check if this rectangle in inside a rectangular zone
	 * 
	 * @param min min coord
	 * @param max max coord
	 * @return is inside
	 */
	public boolean isInRect(Coord min, Coord max)
	{
		return (x >= min.x && x <= max.x) && (y >= min.y && y <= max.y) && (z >= min.z && z <= max.z);
	}
	
	
	/**
	 * Get middle of line to other point
	 * 
	 * @param other other point
	 * @return middle
	 */
	public Coord midTo(Coord other)
	{
		return add(vecTo(other).half_ip());
	}
	
	
	/**
	 * Get copy divided by two
	 * 
	 * @return copy halved
	 */
	public Coord half()
	{
		return copy().half_ip();
	}
	
	
	/**
	 * Divide in place by two
	 * 
	 * @return this
	 */
	public Coord half_ip()
	{
		mul_ip(0.5);
		return this;
	}
	
	
	/**
	 * Multiply each component, in a copy.
	 * 
	 * @param d multiplier
	 * @return changed copy
	 */
	public Coord mul(double d)
	{
		return copy().mul_ip(d);
	}
	
	
	/**
	 * Multiply each component, in place.
	 * 
	 * @param d multiplier
	 * @return this
	 */
	public Coord mul_ip(double d)
	{
		return mul_ip(d, d, d);
	}
	
	
	/**
	 * Multiply each component, in a copy.
	 * 
	 * @param vec vector of multipliers
	 * @return changed copy
	 */
	public Coord mul(Coord vec)
	{
		return copy().mul_ip(vec);
	}
	
	
	/**
	 * Multiply each component, in a copy.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return changed copy
	 */
	public Coord mul(double x, int y)
	{
		return copy().mul_ip(x, y);
	}
	
	
	/**
	 * Multiply each component, in place.<br>
	 * Z is unchanged.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return this
	 */
	public Coord mul_ip(double x, double y)
	{
		return mul_ip(x, y, 1);
	}
	
	
	/**
	 * Multiply each component, in a copy.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return changed copy
	 */
	public Coord mul(double x, double y, double z)
	{
		return copy().mul_ip(x, y, z);
	}
	
	
	/**
	 * Multiply each component, in place.
	 * 
	 * @param vec vector of multipliers
	 * @return this
	 */
	public Coord mul_ip(Coord vec)
	{
		return mul_ip(vec.x, vec.y, vec.z);
	}
	
	
	/**
	 * Multiply each component, in place.
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @param z z multiplier
	 * @return this
	 */
	public Coord mul_ip(double x, double y, double z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	
	public Coord random_offset(double max)
	{
		return copy().random_offset_ip(max);
	}
	
	
	/**
	 * offset randomly in place
	 * 
	 * @param max max +- offset
	 * @return this
	 */
	public Coord random_offset_ip(double max)
	{
		return add(random(1).norm_ip(rand.nextDouble() * max));
	}
	
	
	/**
	 * offset randomly
	 * 
	 * @param min min offset
	 * @param max max offset
	 * @return offset coord
	 */
	public Coord random_offset(double min, double max)
	{
		return copy().random_offset_ip(min, max);
	}
	
	
	/**
	 * offset randomly in place
	 * 
	 * @param min min offset
	 * @param max max offset
	 * @return this
	 */
	public Coord random_offset_ip(double min, double max)
	{
		add_ip(random(min, max));
		return this;
	}
	
	
	/**
	 * Get a copy with rounded coords
	 * 
	 * @return rounded copy
	 */
	public Coord round()
	{
		return copy().round_ip();
	}
	
	
	/**
	 * Round in place
	 * 
	 * @return this
	 */
	public Coord round_ip()
	{
		x = Math.round(x);
		y = Math.round(y);
		z = Math.round(z);
		return this;
	}
	
	
	/**
	 * Set to max values of this and other coord
	 * 
	 * @param other other coord
	 */
	public void setToMax(Coord other)
	{
		x = Math.max(x, other.x);
		y = Math.max(y, other.y);
		z = Math.max(z, other.z);
	}
	
	
	/**
	 * Set to min values of this and other coord
	 * 
	 * @param other other coord
	 */
	public void setToMin(Coord other)
	{
		x = Math.min(x, other.x);
		y = Math.min(y, other.y);
		z = Math.min(z, other.z);
	}
	
	
	/**
	 * Set coordinates to match other coord
	 * 
	 * @param copied coord whose coordinates are used
	 * @return this
	 */
	public Coord setTo(Coord copied)
	{
		return setTo(copied.x, copied.y, copied.z);
	}
	
	
	/**
	 * Set 2D coordinates to
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return this
	 */
	public Coord setTo(Number x, Number y)
	{
		return setTo(x, y, 0);
	}
	
	
	/**
	 * Set 3D coordinates to
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return this
	 */
	public Coord setTo(Number x, Number y, Number z)
	{
		this.x = x.doubleValue();
		this.y = y.doubleValue();
		this.z = z.doubleValue();
		return this;
	}
	
	
	/**
	 * Set X coordinate in a copy
	 * 
	 * @param x x coordinate
	 * @return copy with set coordinate
	 */
	public Coord setX(Number x)
	{
		return copy().setX_ip(x);
	}
	
	
	/**
	 * Set X coordinate in place
	 * 
	 * @param x x coordinate
	 * @return this
	 */
	public Coord setX_ip(Number x)
	{
		this.x = x.doubleValue();
		return this;
	}
	
	
	/**
	 * Set Y coordinate in a copy
	 * 
	 * @param y y coordinate
	 * @return copy with set coordinate
	 */
	public Coord setY(Number y)
	{
		return copy().setY_ip(y);
	}
	
	
	/**
	 * Set Y coordinate in place
	 * 
	 * @param y y coordinate
	 * @return this
	 */
	public Coord setY_ip(Number y)
	{
		this.y = y.doubleValue();
		return this;
	}
	
	
	/**
	 * Set Z coordinate in a copy
	 * 
	 * @param z z coordinate
	 * @return copy with set coordinate
	 */
	public Coord setZ(Number z)
	{
		return copy().setZ_ip(z);
	}
	
	
	/**
	 * Set Z coordinate in place
	 * 
	 * @param z z coordinate
	 * @return this
	 */
	public Coord setZ_ip(Number z)
	{
		this.z = z.doubleValue();
		return this;
	}
	
	
	/**
	 * Get a copy subtracted by vector
	 * 
	 * @param vec offset
	 * @return the offset copy
	 */
	public Coord sub(Coord vec)
	{
		return copy().sub_ip(vec);
	}
	
	
	/**
	 * Get a copy subtracted by 2D coordinate
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return the offset copy
	 */
	public Coord sub(Number x, Number y)
	{
		return copy().sub_ip(x, y);
	}
	
	
	/**
	 * Get a copy subtracted by 3D coordinate
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return the offset copy
	 */
	public Coord sub(Number x, Number y, Number z)
	{
		return copy().sub_ip(x, y, z);
	}
	
	
	/**
	 * Offset by vector in place
	 * 
	 * @param vec offset
	 * @return this
	 */
	public Coord sub_ip(Coord vec)
	{
		return sub_ip(vec.x, vec.y, vec.z);
	}
	
	
	/**
	 * Offset by 2D coordinate in place
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return this
	 */
	public Coord sub_ip(Number x, Number y)
	{
		return sub_ip(x, y, 0);
	}
	
	
	/**
	 * Offset by 3D coordinate in place
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return this
	 */
	public Coord sub_ip(Number x, Number y, Number z)
	{
		this.x -= x.doubleValue();
		this.y -= y.doubleValue();
		this.z -= z.doubleValue();
		return this;
	}
	
	
	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return vector
	 */
	public Coord vecTo(Coord point)
	{
		return point.sub(this);
	}
	
	
	/**
	 * @return X as double
	 */
	public double x()
	{
		return x;
	}
	
	
	/**
	 * @return X as int
	 */
	public int xi()
	{
		return (int) Math.round(x);
	}
	
	
	/**
	 * @return Y as double
	 */
	public double y()
	{
		return y;
	}
	
	
	/**
	 * @return Y as int
	 */
	public int yi()
	{
		return (int) Math.round(y);
	}
	
	
	/**
	 * @return Z as double
	 */
	public double z()
	{
		return z;
	}
	
	
	/**
	 * @return Z as int
	 */
	public int zi()
	{
		return (int) Math.round(z);
	}
	
	
	/**
	 * Get cross product of two vectors
	 * 
	 * @param a 1st vector
	 * @param b 2nd vector
	 * @return cross product
	 */
	public static Coord cross(Coord a, Coord b)
	{
		return a.cross(b);
	}
	
	
	/**
	 * Get dot product of two vectors
	 * 
	 * @param a 1st vector
	 * @param b 2nd vector
	 * @return dot product
	 */
	public static double dot(Coord a, Coord b)
	{
		return a.dot(b);
	}
	
	
	/**
	 * Multiply by other vector, vector multiplication
	 * 
	 * @param vec other vector
	 * @return changed copy
	 */
	public Coord cross(Coord vec)
	{
		return copy().cross_ip(vec);
	}
	
	
	/**
	 * Multiply by other vector, vector multiplication; in place
	 * 
	 * @param vec other vector
	 * @return this
	 */
	public Coord cross_ip(Coord vec)
	{
		//@formatter:off
		setTo(
				y * vec.z - z * vec.y,
				z * vec.x - x * vec.z,
				x * vec.y - y * vec.x
		);
		//@formatter:on
		return this;
	}
	
	
	/**
	 * Get dot product
	 * 
	 * @param vec other vector
	 * @return dot product
	 */
	public double dot(Coord vec)
	{
		return x * vec.x + y * vec.y + z * vec.z;
	}
	
	
	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return negated copy
	 */
	public Coord neg()
	{
		return copy().neg_ip();
	}
	
	
	/**
	 * Negate all coordinates (* -1), in place
	 * 
	 * @return this
	 */
	public Coord neg_ip()
	{
		mul_ip(-1);
		return this;
	}
	
	
	/**
	 * Scale vector to given size
	 * 
	 * @param size size we need
	 * @return scaled vector
	 */
	public Coord norm(double size)
	{
		return copy().norm_ip(size);
	}
	
	
	/**
	 * Scale vector to given size, in place.<br>
	 * Zero vector remains zero.
	 * 
	 * @param size size we need
	 * @return scaled vector
	 */
	public Coord norm_ip(double size)
	{
		if (isZero()) return this;
		
		double k = size / size();
		return mul_ip(k);
	}
	
	
	/**
	 * Get vector size
	 * 
	 * @return size in units
	 */
	public double size()
	{
		if (isZero()) return 0;
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	
	/**
	 * @return true if this coord is a zero coord
	 */
	public boolean isZero()
	{
		return x == 0 && y == 0 && z == 0;
	}
	
	
	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Coord)) return false;
		Coord other = (Coord) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) return false;
		return true;
	}
	
	
	/**
	 * Generate a zero coordinate
	 * 
	 * @return coord of all zeros
	 */
	public static Coord zero()
	{
		return new Coord(0, 0, 0);
	}
	
	
	/**
	 * Generate a unit coordinate
	 * 
	 * @return coord of all ones
	 */
	public static Coord one()
	{
		return new Coord(1, 1, 1);
	}
	
	
	/**
	 * Generate random coord (gaussian)
	 * 
	 * @param max max distance from 0
	 * @return new coord
	 */
	public static Coord random(double max)
	{
		//@formatter:off
		return new Coord(
				Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2),
				Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2),
				Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2)
		);
		//@formatter:on
	}
	
	
	/**
	 * Generate random coord (min-max)
	 * 
	 * @param min min offset
	 * @param max max offset
	 * @return new coord
	 */
	public static Coord random(double min, double max)
	{
		//@formatter:off
		return new Coord(
				(rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min)),
				(rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min)),
				(rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min))
		);
		//@formatter:on
	}
}

package mightypork.utils.math.coord;


import java.util.Random;

import mightypork.utils.math.Calc;


/**
 * Coordinate class, object with three or two double coordinates.<br>
 * 
 * @author MightyPork
 */
public class Coord {

	/** RNG */
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


	/**
	 * Generate random coord (gaussian)
	 * 
	 * @param max max distance from 0
	 * @return new coord
	 */
	public static Coord random(double max)
	{
		return new Coord(Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2), Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2),
				Calc.clampd(rand.nextGaussian() * max, -max * 2, max * 2));
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
		return new Coord((rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min)), (rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min)),
				(rand.nextBoolean() ? -1 : 1) * (min + rand.nextDouble() * (max - min)));
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
	public Coord() {}


	/**
	 * Create coord as a copy of another
	 * 
	 * @param copied copied coord
	 */
	public Coord(Coord copied) {
		this.x = copied.x;
		this.y = copied.y;
		this.z = copied.z;
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
	 * Get a copy offset by vector
	 * 
	 * @param vec offset
	 * @return the offset copy
	 */
	public Coord add(Coord vec)
	{
		return getCopy().add_ip(vec);
	}


	/**
	 * Get a copy offset by 2D coordinate
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return the offset copy
	 */
	public Coord add(Number x, Number y)
	{
		return getCopy().add_ip(x, y);
	}


	/**
	 * Get a copy offset by 3D coordinate
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 * @return the offset copy
	 */
	public Coord add(Number x, Number y, Number z)
	{
		return getCopy().add_ip(x, y, z);
	}


	/**
	 * Offset by vector in place
	 * 
	 * @param vec offset
	 * @return this
	 */
	public Coord add_ip(Coord vec)
	{
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}


	/**
	 * Offset by 2D coordinate in place
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @return this
	 */
	public Coord add_ip(Number x, Number y)
	{
		this.x += x.doubleValue();
		this.y += y.doubleValue();
		return this;
	}


	/**
	 * Offset by 3D coordinate in place
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
	 * @return copy of this vector
	 */
	public Coord getCopy()
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
	 * Get copy divided by number
	 * 
	 * @param d number to divide by
	 * @return divided copy
	 */
	public Coord div(double d)
	{
		return getCopy().div_ip(d);
	}


	/**
	 * Divide by number in place
	 * 
	 * @param d number to divide by
	 * @return this
	 */
	public Coord div_ip(double d)
	{
		if (d == 0) return this;
		x /= d;
		y /= d;
		z /= d;
		return this;
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
	 * Get middle of line to other point
	 * 
	 * @param other other point
	 * @return middle
	 */
	public Coord midTo(Coord other)
	{
		return add(vecTo(other).mul_ip(0.5));
	}


	/**
	 * Multiply by number
	 * 
	 * @param d number
	 * @return multiplied copy
	 */
	public Coord mul(double d)
	{
		return getCopy().mul_ip(d);
	}


	/**
	 * Multiply coords by number
	 * 
	 * @param xd x multiplier
	 * @param yd y multiplier
	 * @param zd z multiplier
	 * @return multiplied copy
	 */
	public Coord mul(double xd, double yd, double zd)
	{
		return getCopy().mul_ip(xd, yd, zd);
	}


	/**
	 * Multiply by number in place
	 * 
	 * @param d multiplier
	 * @return this
	 */
	public Coord mul_ip(double d)
	{
		x *= d;
		y *= d;
		z *= d;
		return this;
	}


	/**
	 * Multiply coords by number in place
	 * 
	 * @param xd x multiplier
	 * @param yd y multiplier
	 * @param zd z multiplier
	 * @return this
	 */
	public Coord mul_ip(double xd, double yd, double zd)
	{
		x *= xd;
		y *= yd;
		z *= zd;
		return this;
	}


	/**
	 * offset randomly
	 * 
	 * @param max max +- offset
	 * @return offset coord
	 */
	public Coord random_offset(double max)
	{
		Coord v = random(1).norm_ip(0.00001 + rand.nextDouble() * max);

		return getCopy().add_ip(v);
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
		return getCopy().add_ip(random(min, max));
	}


	/**
	 * offset randomly in place
	 * 
	 * @param max max +- offset
	 * @return this
	 */
	public Coord random_offset_ip(double max)
	{
		return add(random(max));
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
		add(random(min, max));
		return this;
	}


	/**
	 * Get a copy with rounded coords
	 * 
	 * @return rounded copy
	 */
	public Coord round()
	{
		return getCopy().round_ip();
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
	public void setMax(Coord other)
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
	public void setMin(Coord other)
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
		setTo(copied.x, copied.y, copied.z);
		return this;
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
		setTo(x, y, 0);
		return this;
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
		return getCopy().setX_ip(x);
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
		return getCopy().setY_ip(y);
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
		return getCopy().setZ_ip(z);
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
		return getCopy().sub_ip(vec);
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
		return getCopy().sub_ip(x, y);
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
		return getCopy().sub_ip(x, y, z);
	}


	/**
	 * Offset by vector in place
	 * 
	 * @param vec offset
	 * @return this
	 */
	public Coord sub_ip(Coord vec)
	{
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
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
		this.x -= x.doubleValue();
		this.y -= y.doubleValue();
		return this;
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
	 * @return X as double
	 */
	public double xd()
	{
		return x;
	}


	/**
	 * @return X as float
	 */
	public float xf()
	{
		return (float) x;
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
	 * @return Y as double
	 */
	public double yd()
	{
		return y;
	}


	/**
	 * @return Y as float
	 */
	public float yf()
	{
		return (float) y;
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
	 * @return Z as double
	 */
	public double zd()
	{
		return z;
	}


	/**
	 * @return Z as float
	 */
	public float zf()
	{
		return (float) z;
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
	 * @return copy multiplied
	 */
	public Coord cross(Coord vec)
	{
		return getCopy().cross_ip(vec);
	}


	/**
	 * Multiply by other vector, vector multiplication; in place
	 * 
	 * @param vec other vector
	 * @return this
	 */
	public Coord cross_ip(Coord vec)
	{
		setTo(y * vec.z - z * vec.y, z * vec.x - x * vec.z, x * vec.y - y * vec.x);
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
	 * @return negated coordinate
	 */
	public Coord neg()
	{
		return getCopy().neg_ip();
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
		return getCopy().norm_ip(size);
	}


	/**
	 * Scale vector to given size, in place
	 * 
	 * @param size size we need
	 * @return scaled vector
	 */
	public Coord norm_ip(double size)
	{
		if (size() == 0) {
			z = -1;
		}
		if (size == 0) {
			setTo(0, 0, 0);
			return this;
		}
		double k = size / size();
		mul_ip(k);
		return this;
	}


	/**
	 * Get vector size
	 * 
	 * @return vector size in units
	 */
	public double size()
	{
		return Math.sqrt(x * x + y * y + z * z);
	}


	/**
	 * Get copy divided by two
	 * @return copy halved
	 */
	public Coord half()
	{
		return getCopy().half_ip();
	}


	/**
	 * Divide in place by two
	 * @return this
	 */
	public Coord half_ip()
	{
		mul_ip(0.5);
		return this;
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
	 * @return true if this coord is a zero coord
	 */
	public boolean isZero()
	{
		return x == 0 && y == 0 && z == 0;
	}


	public static Coord zero()
	{
		return new Coord(0, 0, 0);
	}


	public static Coord one()
	{
		return new Coord(1, 1, 1);
	}
}

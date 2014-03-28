package mightypork.utils.math.coord;


import java.util.Random;

import mightypork.utils.math.Calc;
import mightypork.utils.time.Updateable;


/**
 * Coordinate class, object with three or two double coordinates.<br>
 * 
 * @author MightyPork
 */
public class Coord implements Updateable {

	/** Coord [1;1;1] */
	public static final Coord ONE = new Coord(1, 1, 1);

	/** Zero Coord */
	public static final Coord ZERO = new Coord(0, 0);

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

	private double animTime = 0;

	private Vec offs;

	private Coord start;

	private double time = 0;

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
		return copy().add_ip(vec);
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
		return copy().add_ip(x, y);
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
		return copy().add_ip(x, y, z);
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
	 * Start animation
	 * 
	 * @param time anim length
	 */
	public void animate(double time)
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Vec();
		this.time = time;
		animTime = 0;
		offs = start.vecTo(this);
	}


	/**
	 * @return copy of this vector
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
	 * Get copy divided by number
	 * 
	 * @param d number to divide by
	 * @return divided copy
	 */
	public Coord div(double d)
	{
		return copy().div_ip(d);
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


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!obj.getClass().isAssignableFrom(Coord.class)) return false;
		Coord other = (Coord) obj;
		return x == other.x && y == other.y && z == other.z;
	}


	/**
	 * Get current value (animated)
	 * 
	 * @return curent value
	 */
	public Coord getDelta()
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Vec();
		if (isFinished()) return this;
		return new Coord(start.add(offs.scale(animTime / time)));
	}


	@Override
	public int hashCode()
	{
		return Double.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode() ^ Double.valueOf(z).hashCode();
	}


	/**
	 * Get if animation is finished
	 * 
	 * @return is finished
	 */
	public boolean isFinished()
	{
		return animTime >= time;
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
		return add(vecTo(other).scale(0.5));
	}


	/**
	 * Multiply by number
	 * 
	 * @param d number
	 * @return multiplied copy
	 */
	public Coord mul(double d)
	{
		return copy().mul_ip(d);
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
		return copy().mul_ip(xd, yd, zd);
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
		Coord r = random(1);
		Vec v = new Vec(r);
		v.norm_ip(0.00001 + rand.nextDouble() * max);
		return copy().add_ip(v);
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
		return copy().add_ip(random(min, max));
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
	 * Remember position (other changes will be for animation)
	 */
	public void remember()
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Vec();
		start.setTo(this);
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
	 * Get size
	 * 
	 * @return size
	 */
	public double size()
	{
		return new Vec(this).size();
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
	 * Convert X and Y coordinates of this coord to a new CoordI.
	 * 
	 * @return the new CoordI
	 */
	public CoordI toCoordI()
	{
		return new CoordI((int) Math.round(x), (int) Math.round(y));
	}


	@Override
	public String toString()
	{
		return "[ " + x + " ; " + y + " ; " + z + " ]";
	}


	/**
	 * Update delta timing
	 * 
	 * @param delta delta time to add
	 */
	@Override
	public void update(double delta)
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Vec();
		animTime = Calc.clampd(animTime + delta, 0, time);
		if (isFinished()) {
			time = 0;
			animTime = 0;
			start.setTo(this);
		}
	}


	/**
	 * Create vector from this point to other point
	 * 
	 * @param point second point
	 * @return vector
	 */
	public Vec vecTo(Coord point)
	{
		return (Vec) (new Vec(point)).add(new Vec(this).neg());
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
}

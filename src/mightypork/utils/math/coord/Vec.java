package mightypork.utils.math.coord;


/**
 * Vector in 2D/3D space.
 * 
 * @author MightyPork
 */
public class Vec extends Coord {

	/** Vec [1;1;1] */
	@SuppressWarnings("hiding")
	public static final Vec ONE = new Vec(1, 1, 1);
	/** Zero vector */
	@SuppressWarnings("hiding")
	public static final Vec ZERO = new Vec(0, 0, 0);


	/**
	 * Get cross product of two vectors
	 * 
	 * @param a 1st vector
	 * @param b 2nd vector
	 * @return cross product
	 */
	public static Vec cross(Vec a, Vec b)
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
	public static double dot(Vec a, Vec b)
	{
		return a.dot(b);
	}


	/**
	 * Generate random coord (gaussian)
	 * 
	 * @param max max distance from 0
	 * @return new coord
	 */
	public static Vec random(double max)
	{
		return new Vec(Coord.random(max));
	}


	/**
	 * Generate random coord (min-max)
	 * 
	 * @param max max distance from 0
	 * @return new coord
	 */
	public static Vec random(double min, double max)
	{
		return new Vec(Coord.random(min, max));
	}


	/**
	 * Scale vector
	 * 
	 * @param a vector
	 * @param scale
	 * @return scaled copy
	 */
	public static Vec scale(Vec a, double scale)
	{
		return a.scale(scale);
	}


	/**
	 * Get vector size
	 * 
	 * @param vec vector to get size of
	 * @return size in units
	 */
	public static double size(Vec vec)
	{
		return vec.size();
	}


	/**
	 * Create zero vector
	 */
	public Vec() {
		super();
	}


	/**
	 * Create vector as a copy of another
	 * 
	 * @param copied copied vector
	 */
	public Vec(Coord copied) {
		super(copied);
	}


	/**
	 * Create 2D vector
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Vec(Number x, Number y) {
		super(x, y);
	}


	/**
	 * Create 3D vector
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public Vec(Number x, Number y, Number z) {
		super(x, y, z);
	}


	@Override
	public Vec copy()
	{
		return new Vec(this);
	}


	/**
	 * Multiply by other vector, vector multiplication
	 * 
	 * @param vec other vector
	 * @return copy multiplied
	 */
	public Vec cross(Vec vec)
	{
		return copy().cross_ip(vec);
	}


	/**
	 * Multiply by other vector, vector multiplication; in place
	 * 
	 * @param vec other vector
	 * @return this
	 */
	public Vec cross_ip(Vec vec)
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
	public double dot(Vec vec)
	{
		return x * vec.x + y * vec.y + z * vec.z;
	}


	// STATIC

	/**
	 * Negate all coordinates (* -1)
	 * 
	 * @return negated coordinate
	 */
	public Vec neg()
	{
		return copy().neg_ip();
	}


	/**
	 * Negate all coordinates (* -1), in place
	 * 
	 * @return this
	 */
	public Vec neg_ip()
	{
		scale_ip(-1);
		return this;
	}


	/**
	 * Scale vector to given size
	 * 
	 * @param size size we need
	 * @return scaled vector
	 */
	public Vec norm(double size)
	{
		return copy().norm_ip(size);
	}


	/**
	 * Scale vector to given size, in place
	 * 
	 * @param size size we need
	 * @return scaled vector
	 */
	public Vec norm_ip(double size)
	{
		if (size() == 0) {
			z = -1;
		}
		if (size == 0) {
			setTo(0, 0, 0);
			return this;
		}
		double k = size / size();
		scale_ip(k);
		return this;
	}


	/**
	 * offset randomly
	 * 
	 * @param max max +- offset
	 * @return offset coord
	 */
	@Override
	public Vec random_offset(double max)
	{
		return (Vec) super.random_offset(max);
	}


	/**
	 * offset randomly
	 * 
	 * @param min min offset
	 * @param max max offset
	 * @return offset coord
	 */
	@Override
	public Vec random_offset(double min, double max)
	{
		return (Vec) super.random_offset(min, max);
	}


	/**
	 * offset randomly in place
	 * 
	 * @param max max +- offset
	 * @return this
	 */
	@Override
	public Vec random_offset_ip(double max)
	{
		return (Vec) super.random_offset_ip(max);
	}


	/**
	 * offset randomly in place
	 * 
	 * @param min min offset
	 * @param max max offset
	 * @return this
	 */
	@Override
	public Vec random_offset_ip(double min, double max)
	{
		return (Vec) super.random_offset_ip(min, max);
	}


	/**
	 * Multiply all coordinates by factor; scalar multiplication
	 * 
	 * @param factor multiplier
	 * @return copy multiplied
	 */
	public Vec scale(double factor)
	{
		return copy().scale_ip(factor);
	}


	/**
	 * Multiply all coordinates by factor, in place
	 * 
	 * @param factor multiplier
	 * @return this
	 */
	public Vec scale_ip(double factor)
	{
		return (Vec) mul_ip(factor);
	}


	/**
	 * Get vector size
	 * 
	 * @return vector size in units
	 */
	@Override
	public double size()
	{
		return Math.sqrt(x * x + y * y + z * z);
	}

}

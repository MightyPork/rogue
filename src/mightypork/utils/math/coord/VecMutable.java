package mightypork.utils.math.coord;


import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;


/**
 * Mutable coord
 * 
 * @author MightyPork
 */
public abstract class VecMutable extends VecMath<VecMutable> {
	
	/**
	 * Get a variable initialized as zero (0,0,0)
	 * 
	 * @return new mutable vector
	 */
	public static VecMutable zero()
	{
		return make(ZERO);
	}
	
	
	/**
	 * Get a variable initialized as one (1,1,1)
	 * 
	 * @return one mutable vector
	 */
	public static VecMutable one()
	{
		return make(ONE);
	}
	
	
	/**
	 * Make new from coords
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return mutable vector
	 */
	public static VecMutable make(double x, double y)
	{
		return make(x, y, 0);
	}
	
	
	/**
	 * Make new as copy of another
	 * 
	 * @param copied copied vec
	 * @return mutable vector
	 */
	public static VecMutable make(Vec copied)
	{
		return make(copied.x(), copied.y(), copied.z());
	}
	
	
	/**
	 * Make new from coords
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @return mutable vector
	 */
	public static VecMutable make(double x, double y, double z)
	{
		return new VecMutableImpl(x, y, z);
	}
	
	
	/**
	 * Create an animated vector; This way different easing / settings can be
	 * specified for each coordinate.
	 * 
	 * @param animX x animator
	 * @param animY y animator
	 * @param animZ z animator
	 * @return animated mutable vector
	 */
	public static VecMutableAnim makeAnim(AnimDouble animX, AnimDouble animY, AnimDouble animZ)
	{
		return new VecMutableAnim(animX, animY, animZ);
	}
	
	
	/**
	 * Create an animated vector
	 * 
	 * @param animStart initial positioon
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	public static VecMutableAnim makeAnim(Vec animStart, Easing easing)
	{
		return new VecMutableAnim(animStart, easing);
	}
	
	
	/**
	 * Create an animated vector, initialized at 0,0,0
	 * 
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	public static VecMutableAnim makeAnim(Easing easing)
	{
		return new VecMutableAnim(Vec.ZERO, easing);
	}
	
	
	@Override
	public abstract VecMutable result(double x, double y, double z);
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	public VecMutable reset()
	{
		return result(0, 0, 0);
	}
	
	
	/**
	 * Set coordinates to match other coord.
	 * 
	 * @param copied coord whose coordinates are used
	 * @return result
	 */
	public VecMutable setTo(Vec copied)
	{
		return result(copied.x(), copied.y(), copied.z());
	}
	
	
	/**
	 * Set 2D coordinates.<br>
	 * Z is unchanged.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return result
	 */
	public VecMutable setTo(double x, double y)
	{
		return result(x, y, z());
	}
	
	
	/**
	 * Set coordinates.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return result
	 */
	public VecMutable setTo(double x, double y, double z)
	{
		return result(x, y, z);
	}
}

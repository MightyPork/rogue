package mightypork.utils.math.vect;


import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;


/**
 * Mutable coord
 * 
 * @author MightyPork
 */
public abstract class VectMutable extends VectMath<VectMutable> { // returns itself on edit

	/**
	 * Get a variable initialized as zero (0,0,0)
	 * 
	 * @return new mutable vector
	 */
	public static VectMutable zero()
	{
		return make(ZERO);
	}
	
	
	/**
	 * Get a variable initialized as one (1,1,1)
	 * 
	 * @return one mutable vector
	 */
	public static VectMutable one()
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
	public static VectMutable make(double x, double y)
	{
		return make(x, y, 0);
	}
	
	
	/**
	 * Make new as copy of another
	 * 
	 * @param copied copied vec
	 * @return mutable vector
	 */
	public static VectMutable make(Vect copied)
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
	public static VectMutable make(double x, double y, double z)
	{
		return new VectMutableImpl(x, y, z);
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
	public static VectMutableAnim makeAnim(AnimDouble animX, AnimDouble animY, AnimDouble animZ)
	{
		return new VectMutableAnim(animX, animY, animZ);
	}
	
	
	/**
	 * Create an animated vector
	 * 
	 * @param animStart initial positioon
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	public static VectMutableAnim makeAnim(Vect animStart, Easing easing)
	{
		return new VectMutableAnim(animStart, easing);
	}
	
	
	/**
	 * Create an animated vector, initialized at 0,0,0
	 * 
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	public static VectMutableAnim makeAnim(Easing easing)
	{
		return new VectMutableAnim(Vect.ZERO, easing);
	}
	
	
	@Override
	public abstract VectMutable result(double x, double y, double z);
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	public VectMutable reset()
	{
		return result(0, 0, 0);
	}
	
	
	/**
	 * Set coordinates to match other coord.
	 * 
	 * @param copied coord whose coordinates are used
	 * @return result
	 */
	public VectMutable setTo(Vect copied)
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
	public VectMutable setTo(double x, double y)
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
	public VectMutable setTo(double x, double y, double z)
	{
		return result(x, y, z);
	}
}

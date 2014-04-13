package mightypork.utils.math.vect;


import mightypork.utils.annotations.FactoryMethod;


/**
 * Mutable coord
 * 
 * @author MightyPork
 */
public abstract class VectMutable extends VectMathStatic<VectMutable> { // returns itself on edit

	/**
	 * Get a variable initialized as zero (0,0,0)
	 * 
	 * @return new mutable vector
	 */
	@FactoryMethod
	public static VectMutable zero()
	{
		return make(ZERO);
	}
	
	
	/**
	 * Get a variable initialized as one (1,1,1)
	 * 
	 * @return one mutable vector
	 */
	@FactoryMethod
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
	@FactoryMethod
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
	@FactoryMethod
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
	@FactoryMethod
	public static VectMutable make(double x, double y, double z)
	{
		return new VectMutableImpl(x, y, z);
	}
	
	
	/**
	 * Set all to zeros.
	 * 
	 * @return this
	 */
	public VectMutable reset()
	{
		return result(0, 0, 0);
	}
	
	
	/**
	 * Set coordinates to match other coord.
	 * 
	 * @param copied coord whose coordinates are used
	 * @return this
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
	 * @return this
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
	 * @return this
	 */
	public VectMutable setTo(double x, double y, double z)
	{
		return result(x, y, z);
	}
	
	
	/**
	 * Set X coordinate.
	 * 
	 * @param x x coordinate
	 * @return this
	 */
	public abstract VectMutable setX(double x);
	
	
	/**
	 * Set Y coordinate.
	 * 
	 * @param y y coordinate
	 * @return this
	 */
	public abstract VectMutable setY(double y);
	
	
	/**
	 * Set Z coordinate.
	 * 
	 * @param z z coordinate
	 * @return this
	 */
	public abstract VectMutable setZ(double z);
}

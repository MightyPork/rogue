package mightypork.utils.math.vect;


import mightypork.utils.annotations.FactoryMethod;


/**
 * Mutable coord
 * 
 * @author MightyPork
 */
public abstract class VectMutable extends VectView { // returns itself on edit

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
	 */
	public void reset()
	{
		setTo(0, 0, 0);
	}
	
	
	/**
	 * Set coordinates to match other coord.
	 * 
	 * @param copied coord whose coordinates are used
	 */
	public void setTo(Vect copied)
	{
		setTo(copied.x(), copied.y(), copied.z());
	}
	
	
	/**
	 * Set 2D coordinates.<br>
	 * Z is unchanged.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void setTo(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	
	/**
	 * Set coordinates.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public abstract void setTo(double x, double y, double z);
	
	
	/**
	 * Set X coordinate.
	 * 
	 * @param x x coordinate
	 */
	public abstract void setX(double x);
	
	
	/**
	 * Set Y coordinate.
	 * 
	 * @param y y coordinate
	 */
	public abstract void setY(double y);
	
	
	/**
	 * Set Z coordinate.
	 * 
	 * @param z z coordinate
	 */
	public abstract void setZ(double z);
}

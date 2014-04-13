package mightypork.utils.math.vect;




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
		return new VectMutableVariable(x, y, z);
	}
	
	
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

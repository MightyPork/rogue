package mightypork.utils.math.coord;


/**
 * Mutable coord
 * 
 * @author MightyPork
 */
public interface VecMutable extends VecMath<VecMutable> {
	
	/**
	 * Set coordinates to match other coord.
	 * 
	 * @param copied coord whose coordinates are used
	 * @return result
	 */
	VecMutable setTo(Vec copied);
	
	
	/**
	 * Set 2D coordinates.<br>
	 * Z is unchanged.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return result
	 */
	VecMutable setTo(double x, double y);
	
	
	/**
	 * Set coordinates.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return result
	 */
	VecMutable setTo(double x, double y, double z);
}

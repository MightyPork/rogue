package mightypork.utils.math.constraints;


import mightypork.utils.math.vect.VectView;


/**
 * Element holding a vector, used for constraint building.
 * 
 * @author MightyPork
 */
public interface VectBound {
	
	/**
	 * @return the current vector.
	 */
	public VectView getVect();
}

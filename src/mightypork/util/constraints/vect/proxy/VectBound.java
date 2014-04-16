package mightypork.util.constraints.vect.proxy;


import mightypork.util.constraints.vect.Vect;


/**
 * Element holding a vector, used for constraint building.
 * 
 * @author MightyPork
 */
public interface VectBound {
	
	/**
	 * @return the current vector.
	 */
	public Vect getVect();
}

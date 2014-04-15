package mightypork.utils.math.constraints.vect;


/**
 * Pluggable vector constraint
 * 
 * @author MightyPork
 */
public interface PluggableVectBound extends VectBound {
	
	/**
	 * @param num bound to set
	 */
	abstract void setVect(VectBound num);
	
}
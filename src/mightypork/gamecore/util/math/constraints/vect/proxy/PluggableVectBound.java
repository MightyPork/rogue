package mightypork.gamecore.util.math.constraints.vect.proxy;


/**
 * Pluggable vector constraint
 * 
 * @author Ondřej Hruška
 */
public interface PluggableVectBound extends VectBound {
	
	/**
	 * @param num bound to set
	 */
	abstract void setVect(VectBound num);
	
}

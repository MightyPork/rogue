package mightypork.utils.math.constraints;


import mightypork.utils.math.constraints.num.NumBound;


/**
 * Pluggable numeric constraint
 * 
 * @author MightyPork
 */
public interface PluggableNumBound extends NumBound {
	
	/**
	 * @param num bound to set
	 */
	abstract void setNum(NumBound num);
	
}

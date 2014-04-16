package mightypork.util.constraints.num.proxy;


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

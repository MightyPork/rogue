package mightypork.gamecore.util.math.constraints.num.proxy;


/**
 * Pluggable numeric constraint
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface PluggableNumBound extends NumBound {
	
	/**
	 * @param num bound to set
	 */
	abstract void setNum(NumBound num);
	
}

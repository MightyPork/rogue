package mightypork.utils.math.constraints;


/**
 * Can be assigned a context / changed context
 * 
 * @author MightyPork
 */
public interface SettableContext {
	
	/**
	 * Assign a context
	 * 
	 * @param context context
	 */
	public void setContext(ConstraintContext context);
}

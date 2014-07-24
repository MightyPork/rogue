package mightypork.gamecore.initializers;


import mightypork.gamecore.core.modules.App;
import mightypork.utils.annotations.Stub;


/**
 * App initializer. A sequence of initializers is executed once the start()
 * method on App is called. Adding initializers is one way to customize the App
 * behavior and features.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTask {
	
	/**
	 * Run the initalizer on app.
	 * 
	 * @param app the app instance.
	 */
	public abstract void run(App app);
	
	
	/**
	 * Get name of this initializer (for dependency resolver).<br>
	 * The name should be short, snake_case and precise.
	 * 
	 * @return name
	 */
	public abstract String getName();
	
	
	/**
	 * Get what other initializers must be already loaded before this can load.<br>
	 * Depending on itself or creating a circular dependency will cause error.<br>
	 * If the dependencies cannot be satisfied, the initialization sequence will
	 * be aborted.
	 * 
	 * @return array of names of required initializers.
	 */
	@Stub
	public String[] getDependencies()
	{
		return new String[] {};
	}
}

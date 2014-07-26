package mightypork.gamecore.core;


import mightypork.utils.annotations.Stub;


/**
 * App initializer. A sequence of initializers is executed once the start()
 * method on App is called. Adding initializers is one way to customize the App
 * behavior and features.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTask {
	
	protected App app;
	
	
	/**
	 * Assign the initialized app instance to a protected "app" field.
	 * 
	 * @param app app
	 */
	public void bind(App app)
	{
		if (this.app != null) {
			throw new IllegalStateException("App instance is already set.");
		}
		
		this.app = app;
	}
	
	
	/**
	 * An intialization method that is called before the run() method.<br>
	 * This method should be left unimplemented in the task, and can be used to
	 * configure the init task when using it as anonymous inner type.
	 */
	@Stub
	public void init()
	{
	}
	
	
	/**
	 * Run the initalizer on app.
	 */
	public abstract void run();
	
	
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

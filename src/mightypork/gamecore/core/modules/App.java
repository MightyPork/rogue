package mightypork.gamecore.core.modules;


import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.render.RenderModule;


/**
 * Game base class & static subsystem access
 * 
 * @author MightyPork
 */
public class App {
	
	private static App runningInstance;
	private static Backend backend;
	
	
	public App() {
		if (App.isInitialized()) throw new IllegalStateException("App already initialized");
		
		// store current instance in static field
		App.runningInstance = this;
	}
	
	
	/**
	 * Create app with given backend.
	 * 
	 * @param backend backend to use
	 */
	public void setBackend(Backend backend)
	{
		// store used backend in static field
		App.backend = backend;
		
		// initialize the backend
		App.backend.initialize();
	}
	
	
	/**
	 * Throw error if app is not initialized
	 */
	protected static void assertInitialized()
	{
		if (!App.isInitialized()) throw new IllegalStateException("App is not initialized.");
		if (backend == null) throw new IllegalStateException("No backend set!");
	}
	
	
	/**
	 * Check whether the app is initialized (backend assigned).
	 * 
	 * @return is initialized
	 */
	public static boolean isInitialized()
	{
		return runningInstance != null;
	}
	
	
	/**
	 * Get current backend
	 * 
	 * @return the backend
	 */
	public static Backend getBackend()
	{
		assertInitialized();
		return backend;
	}
	
	
	/**
	 * Get renderer instance from the backend
	 * 
	 * @return backend
	 */
	public static RenderModule gfx()
	{
		assertInitialized();
		return backend.getRenderer();
	}
}

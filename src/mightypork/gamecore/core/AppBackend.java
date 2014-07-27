package mightypork.gamecore.core;


import mightypork.gamecore.audio.AudioModule;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.input.InputModule;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * Application backend interface (set of core modules).<br>
 * The goal of this abstraction is to allow easy migration to different
 * environment with different libraries etc. It should be as simple as using
 * different backend.
 * 
 * @author MightyPork
 */
public abstract class AppBackend extends BusNode {
	
	protected App app;
	
	
	/**
	 * Assign an app instance.
	 * 
	 * @param app app
	 */
	public void bind(App app)
	{
		if (this.app != null) {
			throw new IllegalStateException("App already set.");
		}
		this.app = app;
	}
	
	
	/**
	 * Initialize backend modules, add them to event bus.
	 */
	public abstract void initialize();
	
	
	/**
	 * Get graphics module (screen manager, texture and font loader, renderer)
	 * 
	 * @return graphics module
	 */
	public abstract GraphicsModule getGraphics();
	
	
	/**
	 * Get audio module (
	 * 
	 * @return audio module
	 */
	public abstract AudioModule getAudio();
	
	
	/**
	 * Get input module
	 * 
	 * @return input module
	 */
	public abstract InputModule getInput();
}

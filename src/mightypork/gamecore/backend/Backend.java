package mightypork.gamecore.backend;


import mightypork.gamecore.render.GraphicsModule;
import mightypork.gamecore.resources.audio.AudioModule;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * Application backend interface (set of core modules).<br>
 * Backend is created without a bus access, which will be assigned during app
 * initialization.
 * 
 * @author MightyPork
 */
public abstract class Backend extends BusNode {
	
	/**
	 * Initialize backend modules, add them to event bus.<br>
	 * Event bus must already be available in the BusAccess.
	 */
	public abstract void initialize();
	
	
	/**
	 * Get graphics module (renderer)
	 * 
	 * @return graphics module
	 */
	public abstract GraphicsModule getGraphics();
	
	
	/**
	 * Get graphics module (renderer)
	 * 
	 * @return graphics module
	 */
	public abstract AudioModule getAudio();
}

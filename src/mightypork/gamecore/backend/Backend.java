package mightypork.gamecore.backend;


import mightypork.gamecore.render.GraphicsModule;
import mightypork.gamecore.resources.audio.AudioModule;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * Application backend interface (set of core modules).<br>
 * The goal of this abstraction is to allow easy migration to different
 * environment with different libraries etc. It should be as simple as using
 * different backend.
 * 
 * @author MightyPork
 */
public abstract class Backend extends BusNode {
	
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
}

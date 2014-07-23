package mightypork.gamecore.backend;


import mightypork.gamecore.render.RenderModule;
import mightypork.utils.eventbus.BusAccess;
import mightypork.utils.eventbus.clients.RootBusNode;


/**
 * Application backend interface (set of core modules).<br>
 * Backend is created without a bus access, which will be assigned during app
 * initialization.
 * 
 * @author MightyPork
 */
public abstract class Backend extends RootBusNode {
	
	
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
	public abstract RenderModule getRenderer();
}

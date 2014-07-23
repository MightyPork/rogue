package mightypork.gamecore.backend;


import mightypork.gamecore.render.RenderModule;
import mightypork.utils.eventbus.BusAccess;
import mightypork.utils.eventbus.clients.RootBusNode;


/**
 * Application backend interface (set of core modules)
 * 
 * @author MightyPork
 */
public abstract class Backend extends RootBusNode {
	
	public Backend(BusAccess busAccess) {
		super(busAccess);
	}
	
	
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

package mightypork.gamecore.backend;


import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.eventbus.BusAccess;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.interfaces.Destroyable;


/**
 * Abstract application backend module.
 * 
 * @author MightyPork
 */
public abstract class BackendModule extends BusNode implements Destroyable {
	
	/**
	 * Create a module with bus access
	 * 
	 * @param busAccess
	 */
	public BackendModule(BusAccess busAccess) {
		super(busAccess);
	}
	
	@Override
	@DefaultImpl
	public void destroy()
	{	
	}
	
}

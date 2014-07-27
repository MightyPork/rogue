package mightypork.gamecore.core;


import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.interfaces.Destroyable;


/**
 * Abstract application backend module.
 * 
 * @author MightyPork
 */
public abstract class BackendModule extends BusNode implements Destroyable {
	
	public abstract void init();
	
	
	@Override
	@Stub
	public void destroy()
	{
	}
}

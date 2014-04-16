package mightypork.util.control.eventbus.clients;


import mightypork.util.control.Destroyable;
import mightypork.util.control.eventbus.BusAccess;


/**
 * Bus node that should be directly attached to the bus.
 * 
 * @author MightyPork
 */
public abstract class RootBusNode extends BusNode implements Destroyable {
	
	/**
	 * @param busAccess access to bus
	 */
	public RootBusNode(BusAccess busAccess) {
		super(busAccess);
		
		getEventBus().subscribe(this);
	}
	
	
	@Override
	public final void destroy()
	{
		deinit();
		
		getEventBus().unsubscribe(this);
	}
	
	
	/**
	 * Deinitialize the subsystem<br>
	 * (called during destruction)
	 */
	protected abstract void deinit();
	
}

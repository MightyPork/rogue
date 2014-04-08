package mightypork.gamecore.control;

import mightypork.gamecore.control.interf.Destroyable;


/**
 * Bus node that should be directly attached to the bus.
 * 
 * @author MightyPork
 */
public abstract class RootBusNode extends BusNode implements Destroyable {

	public RootBusNode(BusAccess busAccess) {
		super(busAccess);

		bus().subscribe(this);
	}
	
	
	@Override
	public final void destroy()
	{
		deinit();
		
		bus().unsubscribe(this);
	}
	
	
	/**
	 * Deinitialize the subsystem<br>
	 * (called during destruction)
	 */
	protected abstract void deinit();
	
}

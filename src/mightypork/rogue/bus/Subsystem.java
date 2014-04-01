package mightypork.rogue.bus;


import mightypork.rogue.AppAccess;
import mightypork.utils.control.Destroyable;
import mightypork.utils.control.bus.clients.DelegatingClient;
import mightypork.utils.control.bus.clients.ToggleableClient;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus
 * 
 * @author MightyPork
 */
public abstract class Subsystem extends ChildClient implements DelegatingClient, ToggleableClient, Destroyable {
	
	public Subsystem(AppAccess app) {
		super(app);
		
		bus().subscribe(this);
	}
	
	
	@Override
	public final void destroy()
	{
		deinit();
		
		setListening(false);
		
		bus().unsubscribe(this);
	}
	
	
	/**
	 * Deinitialize the subsystem<br>
	 * (called during destruction)
	 */
	protected abstract void deinit();
	
}

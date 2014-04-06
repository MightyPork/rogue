package mightypork.rogue.bus;


import mightypork.rogue.AppAccess;
import mightypork.utils.control.interf.Destroyable;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus
 * 
 * @author MightyPork
 */
public abstract class Subsystem extends ChildClient implements Destroyable {
	
	public Subsystem(AppAccess app) {
		super(app);
		
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

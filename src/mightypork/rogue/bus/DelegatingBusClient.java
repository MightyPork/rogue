package mightypork.rogue.bus;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import mightypork.rogue.AppAccess;
import mightypork.rogue.AppAdapter;
import mightypork.rogue.bus.events.UpdateEvent;
import mightypork.utils.logging.Log;
import mightypork.utils.patterns.Destroyable;
import mightypork.utils.patterns.subscription.MessageBus;
import mightypork.utils.patterns.subscription.clients.DelegatingClient;
import mightypork.utils.patterns.subscription.clients.ToggleableClient;
import mightypork.utils.time.Updateable;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus
 * 
 * @author MightyPork
 */
public abstract class DelegatingBusClient extends AppAdapter implements DelegatingClient, ToggleableClient, Destroyable, Updateable, UpdateEvent.Listener {
	
	/** Subsystem children subscribing to MessageBus */
	private Set<Object> childSubscribers = new HashSet<Object>();
	
	private boolean wantUpdates = true;
	private boolean eventsEnabled = true;
	
	
	public DelegatingBusClient(AppAccess app, boolean updates) {
		super(app);
		
		bus().subscribe(this);
		
		enableUpdates(updates);
		
		init();
	}
	
	
	/**
	 * Add a child subscriber to the {@link MessageBus}.<br>
	 * 
	 * @param client
	 * @return true on success
	 */
	public final boolean addChildSubscriber(Object client)
	{
		if (client == null) return false;
		
		childSubscribers.add(client);
		
		return true;
	}
	
	
	/**
	 * Remove a child subscriber
	 * 
	 * @param client
	 *            subscriber to remove
	 */
	public final void removeChildSubscriber(Object client)
	{
		if (client == null) return;
		
		childSubscribers.remove(client);
	}
	
	
	@Override
	public final Collection<Object> getChildClients()
	{
		return childSubscribers;
	}
	
	
	@Override
	public final boolean doesDelegate()
	{
		return doesSubscribe();
	}
	
	
	/**
	 * Set whether to receive {@link UpdateEvent}s (delta timing, one each
	 * frame).<br>
	 * 
	 * @param enable
	 */
	public final void enableUpdates(boolean enable)
	{
		wantUpdates = enable;
	}
	
	
	@Override
	public final boolean doesSubscribe()
	{
		return eventsEnabled;
	}
	
	
	/**
	 * Set whether events should be received.
	 * 
	 * @param enable
	 */
	public final void enableEvents(boolean enable)
	{
		this.eventsEnabled = enable;
	}
	
	
	@Override
	public final void receive(UpdateEvent event)
	{
		if (wantUpdates) update(event.getDeltaTime());
	}
	
	
	@Override
	public final void destroy()
	{
		deinit();
		
		enableUpdates(false);
		
		bus().unsubscribe(this);
	}
	
	
	@Override
	public void update(double delta)
	{
		Log.w("Client " + getClass().getSimpleName() + " receives updates, but does not override the update() method.");
	}
	
	
	/**
	 * Initialize the subsystem<br>
	 * (called during construction)
	 */
	protected void init()
	{
		// no impl
	}
	
	
	/**
	 * Deinitialize the subsystem<br>
	 * (called during destruction)
	 */
	protected void deinit()
	{
		// no impl
	}
	
}

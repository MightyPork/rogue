package mightypork.gamecore.control;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.clients.DelegatingClient;
import mightypork.gamecore.control.bus.clients.ToggleableClient;
import mightypork.gamecore.control.interf.Destroyable;


/**
 * Client that can be attached to the {@link EventBus}, or added as a child
 * client to another {@link DelegatingClient}
 * 
 * @author MightyPork
 */
public abstract class ChildClient implements BusAccess, DelegatingClient, ToggleableClient, Destroyable {
	
	private BusAccess busAccess;
	
	private final Set<Object> clients = new LinkedHashSet<Object>();
	private boolean listening = true;
	private boolean delegating = true;
	
	
	public ChildClient(BusAccess busAccess) {
		this.busAccess = busAccess;
		
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
	
	
	@Override
	public final Collection<Object> getChildClients()
	{
		return clients;
	}
	
	
	@Override
	public final boolean doesDelegate()
	{
		return delegating;
	}
	
	
	@Override
	public boolean isListening()
	{
		return listening;
	}
	
	
	/**
	 * Add a child subscriber to the {@link EventBus}.<br>
	 * 
	 * @param client
	 */
	public final void addChildClient(Object client)
	{
		if (bus().isClientValid(client)) {
			clients.add(client);
		}
	}
	
	
	/**
	 * Remove a child subscriber
	 * 
	 * @param client subscriber to remove
	 */
	public final void removeChildClient(Object client)
	{
		if (client != null) {
			clients.remove(client);
		}
	}
	
	
	/**
	 * Set whether events should be received.
	 * 
	 * @param listening receive events
	 */
	public final void setListening(boolean listening)
	{
		this.listening = listening;
	}
	
	
	/**
	 * Set whether events should be passed on to child nodes
	 * 
	 * @param delegating
	 */
	public final void setDelegating(boolean delegating)
	{
		this.delegating = delegating;
	}
	
	
	@Override
	public EventBus bus()
	{
		return busAccess.bus();
	}
	
}

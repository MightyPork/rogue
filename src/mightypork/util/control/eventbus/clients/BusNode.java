package mightypork.util.control.eventbus.clients;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.util.control.eventbus.BusAccess;
import mightypork.util.control.eventbus.EventBus;


/**
 * Client that can be attached to the {@link EventBus}, or added as a child
 * client to another {@link DelegatingClient}
 * 
 * @author MightyPork
 */
public abstract class BusNode implements BusAccess, ClientHub {
	
	private final BusAccess busAccess;
	
	private final Set<Object> clients = new LinkedHashSet<>();
	private boolean listening = true;
	private boolean delegating = true;
	
	
	/**
	 * @param busAccess access to bus
	 */
	public BusNode(BusAccess busAccess) {
		this.busAccess = busAccess;
	}
	
	
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
	public final boolean isListening()
	{
		return listening;
	}
	
	
	/**
	 * Add a child subscriber to the {@link EventBus}.<br>
	 * 
	 * @param client
	 */
	@Override
	public final void addChildClient(Object client)
	{
		if (client instanceof RootBusNode) {
			throw new IllegalArgumentException("Cannot nest RootBusNode.");
		}
		
		clients.add(client);
	}
	
	
	/**
	 * Remove a child subscriber
	 * 
	 * @param client subscriber to remove
	 */
	@Override
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
	public final EventBus getEventBus()
	{
		return busAccess.getEventBus();
	}
	
}
package mightypork.rogue.bus;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.rogue.AppAccess;
import mightypork.rogue.AppAdapter;
import mightypork.utils.control.bus.EventBus;
import mightypork.utils.control.bus.clients.DelegatingClient;
import mightypork.utils.control.bus.clients.ToggleableClient;
import mightypork.utils.logging.Log;


/**
 * Client that can be attached to the {@link EventBus}, or added as a child
 * client to another {@link DelegatingClient}
 * 
 * @author MightyPork
 */
public class ChildClient extends AppAdapter implements DelegatingClient, ToggleableClient {
	
	public ChildClient(AppAccess app) {
		super(app);
	}
	
	private Set<Object> clients = new LinkedHashSet<Object>();
	private boolean listening = true;
	private boolean delegating = true;
	
	
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
		} else {
			Log.w("Client rejected by bus: " + client.getClass().getSimpleName());
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
	
}

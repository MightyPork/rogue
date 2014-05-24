package mightypork.gamecore.eventbus.clients;


import java.util.Collection;

import mightypork.gamecore.eventbus.EventBus;


/**
 * Common methods for client hubs (ie delegating vlient implementations)
 * 
 * @author Ondřej Hruška
 */
public interface ClientHub extends DelegatingClient, ToggleableClient {
	
	@Override
	public boolean doesDelegate();
	
	
	@Override
	public Collection<Object> getChildClients();
	
	
	@Override
	public boolean isListening();
	
	
	/**
	 * Add a child subscriber to the {@link EventBus}.<br>
	 * 
	 * @param client
	 */
	public void addChildClient(Object client);
	
	
	/**
	 * Remove a child subscriber
	 * 
	 * @param client subscriber to remove
	 */
	void removeChildClient(Object client);
}

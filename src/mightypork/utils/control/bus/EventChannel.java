package mightypork.utils.control.bus;


import java.util.Collection;
import java.util.HashSet;

import mightypork.utils.control.bus.clients.DelegatingClient;
import mightypork.utils.control.bus.clients.ToggleableClient;
import mightypork.utils.logging.Log;


/**
 * Message channel, module of {@link EventBus}
 * 
 * @author MightyPork
 * @param <EVENT> message type
 * @param <CLIENT> client (subscriber) type
 */
final public class EventChannel<EVENT extends Handleable<CLIENT>, CLIENT> {
	
	private Class<CLIENT> clientClass;
	private Class<EVENT> messageClass;
	private boolean logging = false;
	
	
	public EventChannel(Class<EVENT> messageClass, Class<CLIENT> clientClass) {
		
		if (messageClass == null || clientClass == null) throw new NullPointerException("Null Message or Client class.");
		
		this.clientClass = clientClass;
		this.messageClass = messageClass;
	}
	
	
	public void enableLogging(boolean enable)
	{
		logging = enable;
	}
	
	
	/**
	 * Try to broadcast a message.<br>
	 * If message is of wrong type, <code>false</code> is returned.
	 * 
	 * @param message a message to be sent
	 * @param clients collection of clients
	 * @return true if message was accepted by this channel
	 */
	public boolean broadcast(Object message, Collection<Object> clients)
	{
		if (!canBroadcast(message)) return false;
		
		EVENT evt = messageClass.cast(message);
		
		doBroadcast(evt, clients, new HashSet<Object>());
		
		return true;
	}
	
	
	private void doBroadcast(EVENT message, Collection<Object> clients, Collection<Object> processed)
	{
		for (Object client : clients) {
			
			// circular reference check
			if (processed.contains(client)) {
				if (logging) Log.w("Client already served (subscribing twice?)");
				continue;
			}
			processed.add(client);
			
			// opt-out
			if (client instanceof ToggleableClient) {
				if (!((ToggleableClient) client).isListening()) {
					continue;
				}
			}
			
			sendTo(client, message);
			
			if (client instanceof DelegatingClient) {
				if (((DelegatingClient) client).doesDelegate()) {
					doBroadcast(message, ((DelegatingClient) client).getChildClients(), processed);
				}
			}
		}
	}
	
	
	/**
	 * Send a message to a client.
	 * 
	 * @param client target client
	 * @param message message to send
	 */
	@SuppressWarnings("unchecked")
	private void sendTo(Object client, EVENT message)
	{
		if (clientClass.isInstance(client)) {
			((Handleable<CLIENT>) message).handleBy((CLIENT) client);
			if (logging) Log.f3("<bus> Received by: " + client);
		}
	}
	
	
	/**
	 * Check if the given message can be broadcasted by this
	 * {@link EventChannel}
	 * 
	 * @param message event object
	 * @return can be broadcasted
	 */
	public boolean canBroadcast(Object message)
	{
		return message != null && messageClass.isInstance(message);
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 13;
		int result = 1;
		result = prime * result + ((clientClass == null) ? 0 : clientClass.hashCode());
		result = prime * result + ((messageClass == null) ? 0 : messageClass.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof EventChannel)) return false;
		EventChannel<?, ?> other = (EventChannel<?, ?>) obj;
		if (clientClass == null) {
			if (other.clientClass != null) return false;
		} else if (!clientClass.equals(other.clientClass)) return false;
		if (messageClass == null) {
			if (other.messageClass != null) return false;
		} else if (!messageClass.equals(other.messageClass)) return false;
		return true;
	}
	
	
	@Override
	public String toString()
	{
		return "CHANNEL( " + messageClass.getSimpleName() + " -> " + clientClass.getSimpleName() + " )";
	}
	
	
	/**
	 * Create an instance for given types
	 * 
	 * @param messageClass event class
	 * @param clientClass client class
	 * @return the broadcaster
	 */
	public static <F_EVENT extends Handleable<F_CLIENT>, F_CLIENT> EventChannel<F_EVENT, F_CLIENT> create(Class<F_EVENT> messageClass, Class<F_CLIENT> clientClass)
	{
		return new EventChannel<F_EVENT, F_CLIENT>(messageClass, clientClass);
	}
	
}

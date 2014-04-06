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
final public class EventChannel<EVENT extends Event<CLIENT>, CLIENT> {
	
	private final Class<CLIENT> clientClass;
	private final Class<EVENT> messageClass;
	private boolean logging = false;
	
	
	/**
	 * Create a channel
	 * 
	 * @param messageClass event class
	 * @param clientClass client class
	 */
	public EventChannel(Class<EVENT> messageClass, Class<CLIENT> clientClass) {
		
		if (messageClass == null || clientClass == null) throw new NullPointerException("Null Message or Client class.");
		
		this.clientClass = clientClass;
		this.messageClass = messageClass;
	}
	
	
	/**
	 * Enable logging of non-warning debug messages.
	 * 
	 * @param logging enable logging
	 */
	public void enableLogging(boolean logging)
	{
		this.logging = logging;
	}
	
	
	/**
	 * Try to broadcast a message.<br>
	 * If message is of wrong type, <code>false</code> is returned.
	 * 
	 * @param message a message to be sent
	 * @param clients collection of clients
	 * @return true if message was sent
	 */
	public boolean broadcast(Event<?> message, Collection<Object> clients)
	{
		if (!canBroadcast(message)) return false;
		
		final EVENT evt = messageClass.cast(message);
		
		return doBroadcast(evt, clients, new HashSet<Object>());
	}
	
	
	/**
	 * Send the message
	 * 
	 * @param message sent message
	 * @param clients subscribing clients
	 * @param processed clients already processed
	 * @return success
	 */
	private boolean doBroadcast(final EVENT message, final Collection<Object> clients, final Collection<Object> processed)
	{
		boolean sent = false;
		final boolean singular = message.getClass().isAnnotationPresent(SingularEvent.class);
		
		for (final Object client : clients) {
			
			// exclude obvious non-clients
			if (!isClientValid(client)) {
				continue;
			}
			
			// avoid executing more times
			if (processed.contains(client)) {
				Log.w("<bus> Client already served: " + Log.str(client));
				continue;
			}
			processed.add(client);
			
			// opt-out
			if (client instanceof ToggleableClient) {
				if (!((ToggleableClient) client).isListening()) {
					if (logging) Log.f3("<bus> Client disabled: " + Log.str(client));
					continue;
				}
			}
			
			sent |= sendTo(client, message);
			
			// singular event ain't no whore, handled once only.
			if (sent && singular) return true;
			
			// pass on to delegated clients
			if (client instanceof DelegatingClient) {
				if (((DelegatingClient) client).doesDelegate()) {
					
					final Collection<Object> children = ((DelegatingClient) client).getChildClients();
					
					if (children != null && children.size() > 0) {
						sent |= doBroadcast(message, children, processed);
					}
					
				} else {
					if (logging) Log.f3("<bus> Client not delegating: " + Log.str(client));
				}
			}
		}
		
		return sent;
	}
	
	
	/**
	 * Send a message to a client.
	 * 
	 * @param client target client
	 * @param message message to send
	 * @return success
	 */
	@SuppressWarnings("unchecked")
	private boolean sendTo(Object client, EVENT message)
	{
		if (isClientOfType(client)) {
			if (logging) Log.f3("<bus> Delivered " + Log.str(message) + " to " + Log.str(client));
			((Event<CLIENT>) message).handleBy((CLIENT) client);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Check if the given message can be broadcasted by this
	 * {@link EventChannel}
	 * 
	 * @param message event object
	 * @return can be broadcasted
	 */
	public boolean canBroadcast(Event<?> message)
	{
		return message != null && messageClass.isInstance(message);
	}
	
	
	/**
	 * Create an instance for given types
	 * 
	 * @param messageClass event class
	 * @param clientClass client class
	 * @return the broadcaster
	 */
	public static <F_EVENT extends Event<F_CLIENT>, F_CLIENT> EventChannel<F_EVENT, F_CLIENT> create(Class<F_EVENT> messageClass, Class<F_CLIENT> clientClass)
	{
		return new EventChannel<F_EVENT, F_CLIENT>(messageClass, clientClass);
	}
	
	
	/**
	 * Check if client is of channel type
	 * 
	 * @param client client
	 * @return is of type
	 */
	private boolean isClientOfType(Object client)
	{
		return clientClass.isInstance(client);
	}
	
	
	/**
	 * Check if the channel is compatible with given
	 * 
	 * @param client client
	 * @return is supported
	 */
	public boolean isClientValid(Object client)
	{
		return isClientOfType(client) || (client instanceof DelegatingClient);
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
		final EventChannel<?, ?> other = (EventChannel<?, ?>) obj;
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
		return "{ " + Log.str(messageClass) + " => " + Log.str(clientClass) + " }";
	}
}

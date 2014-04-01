package mightypork.utils.control.bus;


import java.util.Collection;
import java.util.HashSet;

import mightypork.utils.control.bus.clients.DelegatingClient;
import mightypork.utils.control.bus.clients.ToggleableClient;
import mightypork.utils.logging.Log;


/**
 * Message channel, module of {@link MessageBus}
 * 
 * @author MightyPork
 * @param <MESSAGE> message type
 * @param <CLIENT> client (subscriber) type
 */
final public class MessageChannel<MESSAGE extends Handleable<CLIENT>, CLIENT> {
	
	private Class<CLIENT> clientClass;
	private Class<MESSAGE> messageClass;
	
	
	public MessageChannel(Class<MESSAGE> messageClass, Class<CLIENT> clientClass) {
		
		if (messageClass == null || clientClass == null) throw new NullPointerException("Null Message or Client class.");
		
		this.clientClass = clientClass;
		this.messageClass = messageClass;
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
		
		MESSAGE evt = messageClass.cast(message);
		
		doBroadcast(evt, clients, new HashSet<Object>());
		
		return true;
	}
	
	
	private void doBroadcast(MESSAGE message, Collection<Object> clients, Collection<Object> processed)
	{
		for (Object client : clients) {
			
			// circular reference check
			if (processed.contains(client)) {
				Log.w("Client already served (subscribing twice?)");
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
	private void sendTo(Object client, MESSAGE message)
	{
		if (clientClass.isInstance(client)) {
			((Handleable<CLIENT>) message).handleBy((CLIENT) client);
		}
	}
	
	
	/**
	 * Check if the given message can be broadcasted by this
	 * {@link MessageChannel}
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
		if (!(obj instanceof MessageChannel)) return false;
		MessageChannel<?, ?> other = (MessageChannel<?, ?>) obj;
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
	public static <F_MESSAGE extends Handleable<F_CLIENT>, F_CLIENT> MessageChannel<F_MESSAGE, F_CLIENT> create(Class<F_MESSAGE> messageClass, Class<F_CLIENT> clientClass)
	{
		return new MessageChannel<F_MESSAGE, F_CLIENT>(messageClass, clientClass);
	}
	
}

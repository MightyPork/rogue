package mightypork.utils.control.bus;


import java.util.Collection;
import java.util.LinkedHashSet;

import mightypork.utils.logging.Log;


/**
 * An event bus, accommodating multiple {@link EventChannel}s.
 * 
 * @author MightyPork
 */
final public class EventBus {
	
	private Collection<EventChannel<?, ?>> channels = new LinkedHashSet<EventChannel<?, ?>>();
	
	private Collection<Object> clients = new LinkedHashSet<Object>();
	
	private boolean warn_unsent = true;
	
	
	/**
	 * Add a {@link EventChannel} to this bus.<br>
	 * If a channel of matching types is already added, it is returned instead.
	 * 
	 * @param channel channel to be added
	 * @return the channel that's now in the bus
	 */
	public EventChannel<?, ?> addChannel(EventChannel<?, ?> channel)
	{
		// if the channel already exists, return this instance instead.
		for (EventChannel<?, ?> ch : channels) {
			if (ch.equals(channel)) {
				Log.w("Channel of type " + channel + " already registered.");
				return ch;
			}
		}
		
		channels.add(channel);
		
		return channel;
	}
	
	
	/**
	 * Remove a {@link EventChannel} from this bus
	 * 
	 * @param channel true if channel was removed
	 */
	public void removeChannel(EventChannel<?, ?> channel)
	{
		channels.remove(channel);
	}
	
	
	/**
	 * Broadcast a message
	 * 
	 * @param message message
	 * @return true if message was accepted by at least one channel
	 */
	public boolean broadcast(Object message)
	{
		boolean sent = false;
		
		for (EventChannel<?, ?> b : channels) {
			sent |= b.broadcast(message, clients);
		}
		
		if (!sent && warn_unsent) Log.w("Message not accepted by any channel: " + message);
		
		return sent;
	}
	
	
	/**
	 * Connect a client to the bus. The client will be connected to all current
	 * and future channels, until removed from the bus.
	 * 
	 * @param client the client
	 * @return true on success
	 */
	public boolean subscribe(Object client)
	{
		if (client == null) return false;
		
		clients.add(client);
		
		return true;
	}
	
	
	/**
	 * Disconnect a client from the bus.
	 * 
	 * @param client the client
	 */
	public void unsubscribe(Object client)
	{
		clients.remove(client);
	}
	
	
	/**
	 * Enable logging of unsent messages
	 * 
	 * @param enable
	 */
	public void enableLoggingUnsent(boolean enable)
	{
		this.warn_unsent = enable;
	}
	
	
	/**
	 * Add a channel for given message and client type.
	 * 
	 * @param messageClass message type
	 * @param clientClass client type
	 * @return the created channel instance
	 */
	public <F_EVENT extends Handleable<F_CLIENT>, F_CLIENT> EventChannel<?, ?> createChannel(Class<F_EVENT> messageClass, Class<F_CLIENT> clientClass)
	{
		EventChannel<F_EVENT, F_CLIENT> bc = new EventChannel<F_EVENT, F_CLIENT>(messageClass, clientClass);
		return addChannel(bc);
	}
	
}
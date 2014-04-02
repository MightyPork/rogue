package mightypork.utils.control.bus;


import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

import mightypork.utils.control.bus.events.DestroyEvent;
import mightypork.utils.control.bus.events.UpdateEvent;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.logging.Log;


/**
 * An event bus, accommodating multiple {@link EventChannel}s.
 * 
 * @author MightyPork
 */
final public class EventBus {
	
	private Collection<EventChannel<?, ?>> channels = new CopyOnWriteArraySet<EventChannel<?, ?>>();
	
	private Collection<Object> clients = new CopyOnWriteArraySet<Object>();
	
	private boolean logging = false;
	
	
	public EventBus() {
		// default channels
		createChannel(DestroyEvent.class, Destroyable.class);
		createChannel(UpdateEvent.class, Updateable.class);
	}
	
	
	public void enableLogging(boolean enable)
	{
		logging = enable;
	}
	
	
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
				if (logging) Log.w("Channel of type " + channel + " already registered.");
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
		if (logging) Log.f3("<bus> Broadcasting: " + message);
		
		boolean sent = false;
		
		for (EventChannel<?, ?> b : channels) {
			sent |= b.broadcast(message, clients);
		}
		
		if (!sent && logging) Log.w("Message not accepted by any channel: " + message);
		
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
	 * Add a channel for given message and client type.
	 * 
	 * @param messageClass message type
	 * @param clientClass client type
	 * @return the created channel instance
	 */
	public <F_EVENT extends Handleable<F_CLIENT>, F_CLIENT> EventChannel<?, ?> createChannel(Class<F_EVENT> messageClass, Class<F_CLIENT> clientClass)
	{
		EventChannel<F_EVENT, F_CLIENT> channel = EventChannel.create(messageClass, clientClass);
		channel.enableLogging(logging);
		return addChannel(channel);
	}
	
}

package mightypork.utils.control.bus;


import java.util.Collection;
import java.util.HashSet;

import mightypork.utils.control.bus.clients.DelegatingClient;
import mightypork.utils.control.bus.clients.ToggleableClient;
import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.SingularEvent;
import mightypork.utils.logging.Log;


/**
 * Event delivery channel, module of {@link EventBus}
 * 
 * @author MightyPork
 * @param <EVENT> event type
 * @param <CLIENT> client (subscriber) type
 */
final public class EventChannel<EVENT extends Event<CLIENT>, CLIENT> {
	
	private final Class<CLIENT> clientClass;
	private final Class<EVENT> eventClass;
	
	
	/**
	 * Create a channel
	 * 
	 * @param eventClass event class
	 * @param clientClass client class
	 */
	public EventChannel(Class<EVENT> eventClass, Class<CLIENT> clientClass) {
		
		if (eventClass == null || clientClass == null) {
			throw new NullPointerException("Null Event or Client class.");
		}
		
		this.clientClass = clientClass;
		this.eventClass = eventClass;
	}
	
	
	/**
	 * Try to broadcast a event.<br>
	 * If event is of wrong type, <code>false</code> is returned.
	 * 
	 * @param event a event to be sent
	 * @param clients collection of clients
	 * @return true if event was sent
	 */
	public boolean broadcast(Event<?> event, Collection<Object> clients)
	{
		if (!canBroadcast(event)) return false;
				
		return doBroadcast(eventClass.cast(event), clients, new HashSet<Object>());
	}
	
	
	/**
	 * Send the event
	 * 
	 * @param event sent event
	 * @param clients subscribing clients
	 * @param processed clients already processed
	 * @return success
	 */
	private boolean doBroadcast(final EVENT event, final Collection<Object> clients, final Collection<Object> processed)
	{
		boolean sent = false;
		final boolean singular = event.getClass().isAnnotationPresent(SingularEvent.class);
		
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
				if (!((ToggleableClient) client).isListening()) continue;
			}
			
			sent |= sendTo(client, event);
			
			// singular event ain't no whore, handled once only.
			if (sent && singular) return true;
			
			// pass on to delegated clients
			if (client instanceof DelegatingClient) {
				if (((DelegatingClient) client).doesDelegate()) {
					
					final Collection<Object> children = ((DelegatingClient) client).getChildClients();
					
					if (children != null && children.size() > 0) {
						sent |= doBroadcast(event, children, processed);
					}
					
				}
			}
		}
		
		return sent;
	}
	
	
	/**
	 * Send an event to a client.
	 * 
	 * @param client target client
	 * @param event event to send
	 * @return success
	 */
	@SuppressWarnings("unchecked")
	private boolean sendTo(Object client, EVENT event)
	{
		if (isClientOfType(client)) {
			((Event<CLIENT>) event).handleBy((CLIENT) client);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Check if the given event can be broadcasted by this
	 * {@link EventChannel}
	 * 
	 * @param event event object
	 * @return can be broadcasted
	 */
	public boolean canBroadcast(Event<?> event)
	{
		return event != null && eventClass.isInstance(event);
	}
	
	
	/**
	 * Create an instance for given types
	 * 
	 * @param eventClass event class
	 * @param clientClass client class
	 * @return the broadcaster
	 */
	public static <F_EVENT extends Event<F_CLIENT>, F_CLIENT> EventChannel<F_EVENT, F_CLIENT> create(Class<F_EVENT> eventClass, Class<F_CLIENT> clientClass)
	{
		return new EventChannel<F_EVENT, F_CLIENT>(eventClass, clientClass);
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
		result = prime * result + ((eventClass == null) ? 0 : eventClass.hashCode());
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
		if (eventClass == null) {
			if (other.eventClass != null) return false;
		} else if (!eventClass.equals(other.eventClass)) return false;
		return true;
	}
	
	
	@Override
	public String toString()
	{
		return "{ " + Log.str(eventClass) + " => " + Log.str(clientClass) + " }";
	}
}

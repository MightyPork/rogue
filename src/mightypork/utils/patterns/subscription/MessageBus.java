package mightypork.utils.patterns.subscription;

import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.utils.logging.Log;


/**
 * An event bus, accommodating multiple {@link MessageChannel}s.
 * 
 * @author MightyPork
 */
public class MessageBus implements Subscribable {

	private Set<MessageChannel<?, ?>> channels = new LinkedHashSet<MessageChannel<?, ?>>();
	private Set<Object> clients = new LinkedHashSet<Object>();


	/**
	 * Add a {@link MessageChannel} to this bus.<br>
	 * If a channel of matching types is already added, it is returned instead.
	 * 
	 * @param channel channel to be added
	 * @return the channel that's now in the bus
	 */
	public MessageChannel<?, ?> addChannel(MessageChannel<?, ?> channel)
	{
		// if the channel already exists, return this instance instead.
		for (MessageChannel<?, ?> ch : channels) {
			if (ch.equals(channel)) {
				Log.w("Channel of type "+channel+" already registered.");
				return ch;
			}
		}

		channels.add(channel);

		for (Object c : clients) {
			channel.addSubscriber(c);
		}

		return channel;
	}


	/**
	 * Remove a {@link MessageChannel} from this bus
	 * 
	 * @param channel true if channel was removed
	 */
	public void removeChannel(MessageChannel<?, ?> channel)
	{
		channels.remove(channel);

		for (Object c : clients) {
			channel.removeSubscriber(c);
		}
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
		for (MessageChannel<?, ?> b : channels) {
			sent |= b.broadcast(message);
		}
		return sent;
	}


	/**
	 * Subscribe a client to the bus. The client will be connected to all
	 * current and future channels, until removed from the bus.
	 * 
	 * @return <code>true</code>
	 */
	@Override
	public boolean addSubscriber(Object client)
	{
		for (MessageChannel<?, ?> b : channels) {
			b.addSubscriber(client);
		}

		clients.add(client);

		return true;
	}


	@Override
	public void removeSubscriber(Object client)
	{
		for (MessageChannel<?, ?> b : channels) {
			b.removeSubscriber(client);
		}

		clients.remove(client);
	}


	/**
	 * Add a channel for given message and client type.
	 * 
	 * @param messageClass message type
	 * @param clientClass client type
	 * @return the created channel instance
	 */
	public <F_MESSAGE extends Handleable<F_CLIENT>, F_CLIENT> MessageChannel<?, ?> registerMessageType(Class<F_MESSAGE> messageClass, Class<F_CLIENT> clientClass)
	{
		MessageChannel<F_MESSAGE, F_CLIENT> bc = new MessageChannel<F_MESSAGE, F_CLIENT>(messageClass, clientClass);
		return addChannel(bc);
	}

}

package mightypork.utils.patterns.subscription;


import java.util.HashSet;
import java.util.Set;


/**
 * Message subsystem (broadcaster with clients) to which clients can subscribe.<br>
 * If more than one type of message is needed, {@link MessageBus} is a better
 * choice.
 * 
 * @author MightyPork
 * @param <MESSAGE> message type
 * @param <CLIENT> client (subscriber) type
 */
public final class MessageChannel<MESSAGE extends Handleable<CLIENT>, CLIENT> implements Subscribable {

	private Set<CLIENT> clients = new HashSet<CLIENT>();

	private Class<CLIENT> clientClass;
	private Class<MESSAGE> messageClass;


	public MessageChannel(Class<MESSAGE> messageClass, Class<CLIENT> clientClass) {
		
		if(messageClass == null || clientClass == null) throw new IllegalArgumentException("Null Message or Client class.");
		
		this.clientClass = clientClass;
		this.messageClass = messageClass;
	}


	@Override
	public boolean addSubscriber(Object client)
	{
		if (!canSubscribe(client)) return false;

		clients.add(clientClass.cast(client));
		return true;
	}


	@Override
	public void removeSubscriber(Object client)
	{
		clients.remove(client);
	}


	/**
	 * Try to broadcast a message.<br>
	 * If message is of wrong type, <code>false</code> is returned.
	 * 
	 * @param message a message to send
	 * @return true if message was sent
	 */
	public boolean broadcast(Object message)
	{

		if (!canBroadcast(message)) return false;

		MESSAGE evt = messageClass.cast(message);

		for (CLIENT client : clients) {
			sendTo(client, evt);
		}

		return true;
	}


	/**
	 * Send a message to a client
	 * 
	 * @param client target client
	 * @param message message to send
	 */
	private void sendTo(CLIENT client, MESSAGE message)
	{
		((Handleable<CLIENT>) message).handleBy(client);
	}


	/**
	 * Check if the given message can be broadcasted by this
	 * {@link MessageChannel}
	 * 
	 * @param maybeMessage event object
	 * @return can be broadcasted
	 */
	private boolean canBroadcast(Object maybeMessage)
	{
		return messageClass.isInstance(maybeMessage);
	}


	/**
	 * Check if a client can subscribe to this {@link MessageChannel}
	 * 
	 * @param maybeClient client asking for subscription
	 * @return can subscribe
	 */
	public boolean canSubscribe(Object maybeClient)
	{
		return clientClass.isInstance(maybeClient);
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + clientClass.getName().hashCode();
		result = prime * result + messageClass.getName().hashCode();
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof MessageChannel)) return false;
		
		MessageChannel<?, ?> other = (MessageChannel<?, ?>) obj;

		if (!clientClass.getName().equals(other.clientClass.getName())) return false;

		if (!messageClass.getName().equals(other.messageClass.getName())) return false;

		return true;
	}
	
	@Override
	public String toString()
	{
		return "CHANNEL( "+messageClass.getSimpleName()+" -> "+clientClass.getSimpleName()+" )";
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

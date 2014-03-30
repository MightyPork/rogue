package mightypork.utils.patterns.subscription;


/**
 * Subscribable object
 * 
 * @author MightyPork
 */
public interface Subscribable {

	/**
	 * Subscribe a client to messages from this object
	 * 
	 * @param client a subscribing client
	 * @return true if client is now subscribed
	 */
	public boolean addSubscriber(Object client);


	/**
	 * Unsubscribe a client from from this object
	 * 
	 * @param client a clientto unsubscribe
	 */
	public void removeSubscriber(Object client);
}

package mightypork.utils.patterns.subscription.clients;


import java.util.Collection;


/**
 * Client containing child clients
 * 
 * @author MightyPork
 */
public interface DelegatingClient {

	/**
	 * @return collection of child clients. Can not be null.
	 */
	public Collection<Object> getChildClients();


	/**
	 * @return true if delegating is active
	 */
	public boolean doesDelegate();

}

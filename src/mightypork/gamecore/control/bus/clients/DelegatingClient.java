package mightypork.gamecore.control.bus.clients;


import java.util.Collection;


/**
 * Client containing child clients. According to the contract, if the collection
 * of clients is ordered, the clients will be served in that order. In any case,
 * the {@link DelegatingClient} itself will be served beforehand.
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

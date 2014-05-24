package mightypork.gamecore.eventbus.clients;


import java.util.ArrayList;


/**
 * Array-list with varargs constructor
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ClientList extends ArrayList<Object> {
	
	public ClientList(Object... clients)
	{
		for (final Object c : clients) {
			super.add(c);
		}
	}
	
}

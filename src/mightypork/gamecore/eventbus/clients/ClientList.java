package mightypork.gamecore.eventbus.clients;


import java.util.ArrayList;
import java.util.Collection;

import mightypork.gamecore.gui.Enableable;


/**
 * Array-list with varargs constructor
 * 
 * @author MightyPork
 */
public class ClientList extends ArrayList<Object> {
	
	public ClientList(Object... clients) {
		for (Object c : clients) {
			super.add(c);
		}
	}
	
}

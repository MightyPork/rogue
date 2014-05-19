package mightypork.gamecore.eventbus.clients;


import java.util.ArrayList;
import java.util.Collection;

import mightypork.gamecore.gui.Enableable;


/**
 * Basic delegating client
 * 
 * @author MightyPork
 */
public class DelegatingList extends ClientList implements DelegatingClient, Enableable {
	
	private boolean enabled = true;
	
	
	public DelegatingList(Object... clients) {
		super(clients);
	}
	
	
	@Override
	public Collection<?> getChildClients()
	{
		return this;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return isEnabled();
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		enabled = yes;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
}

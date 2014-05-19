package mightypork.gamecore.gui.components.input;


import java.util.Collection;

import mightypork.gamecore.eventbus.clients.ClientList;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.eventbus.clients.DelegatingList;
import mightypork.gamecore.gui.components.Component;


public class ClickableWrapper extends ClickableComponent implements DelegatingClient {
	
	private final Component wrapped;
	private final ClientList list;
	
	
	public ClickableWrapper(Component wrapped) {
		this.wrapped = wrapped;
		wrapped.setRect(this);
		
		list = new ClientList(wrapped);
	}
	
	
	@Override
	public Collection<?> getChildClients()
	{
		return list;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return true;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return super.isEnabled() && wrapped.isEnabled();
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		super.setEnabled(yes);
		wrapped.setEnabled(yes);
	}
	
	
	@Override
	protected void renderComponent()
	{
		wrapped.render();
	}
	
}

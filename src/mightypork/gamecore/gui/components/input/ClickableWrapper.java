package mightypork.gamecore.gui.components.input;


import java.util.Collection;

import mightypork.gamecore.gui.components.Component;
import mightypork.utils.eventbus.clients.ClientList;
import mightypork.utils.eventbus.clients.DelegatingClient;


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
	protected void renderComponent()
	{
		wrapped.render();
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		if (yes != super.isDirectlyEnabled()) {
			super.setEnabled(yes);
			wrapped.setIndirectlyEnabled(yes);
		}
	}
	
	
	@Override
	public void setIndirectlyEnabled(boolean yes)
	{
		super.setIndirectlyEnabled(yes);
		wrapped.setIndirectlyEnabled(yes);
	}
	
}

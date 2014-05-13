package mightypork.gamecore.gui.components.layout;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.Component;


public class ClickableWrapper extends ClickableComponent implements DelegatingClient {
	
	
	private final Component wrapped;
	private final List<Component> list;
	
	
	public ClickableWrapper(Component wrapped)
	{
		this.wrapped = wrapped;
		wrapped.setRect(this);
		
		list = new ArrayList<>(1);
		list.add(wrapped);
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

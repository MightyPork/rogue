package mightypork.gamecore.gui.components.layout.linear;


import java.util.Collection;

import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LinearComponent;
import mightypork.utils.eventbus.clients.ClientList;
import mightypork.utils.eventbus.clients.DelegatingClient;


/**
 * Converts a component into a linear component
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class AbstractLinearWrapper extends LinearComponent implements DelegatingClient {
	
	protected final Component wrapped;
	private final ClientList list;
	
	
	/**
	 * @param wrapped wrapped component. Can be null.
	 */
	public AbstractLinearWrapper(Component wrapped)
	{
		this.wrapped = wrapped;
		if (wrapped != null) {
			if (wrapped instanceof LinearComponent) {
				((LinearComponent) wrapped).setHeight(height());
				((LinearComponent) wrapped).setOrigin(origin());
			} else {
				wrapped.setRect(this);
			}
		}
		
		list = new ClientList(wrapped);
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (wrapped != null) wrapped.render();
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

package mightypork.gamecore.gui.components.layout.linear;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LinearComponent;


/**
 * Converts a component into a linear component
 * 
 * @author MightyPork
 */
public abstract class AbstractLinearWrapper extends LinearComponent implements DelegatingClient {
	
	protected final Component wrapped;
	private final List<Component> list;
	
	
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
		
		list = new ArrayList<>(1);
		list.add(wrapped);
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
}
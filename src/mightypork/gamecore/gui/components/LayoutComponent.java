package mightypork.gamecore.gui.components;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.utils.eventbus.clients.ClientHub;
import mightypork.utils.eventbus.clients.DelegatingList;
import mightypork.utils.math.constraints.rect.RectBound;


public abstract class LayoutComponent extends BaseComponent implements ClientHub {
	
	private final DelegatingList clientList;
	final LinkedList<Component> components = new LinkedList<>();
	
	
	public LayoutComponent(RectBound context) {
		this.clientList = new DelegatingList();
		setRect(context);
		enableCaching(true); // layout is typically updated only when screen resizes.
	}
	
	
	public LayoutComponent() {
		this(null);
	}
	
	
	@Override
	public Collection<Object> getChildClients()
	{
		return clientList;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return clientList.doesDelegate();
	}
	
	
	@Override
	public boolean isListening()
	{
		return clientList.isListening();
	}
	
	
	@Override
	public void addChildClient(Object client)
	{
		clientList.add(client);
	}
	
	
	@Override
	public void removeChildClient(Object client)
	{
		clientList.remove(client);
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		if (isDirectlyEnabled() != yes) {
			super.setEnabled(yes);
			
			for (final Component c : components) {
				c.setIndirectlyEnabled(yes);
			}
		}
	}
	
	
	/**
	 * Connect to bus and add to element list
	 * 
	 * @param component added component, whose context has already been set.
	 */
	protected final void attach(Component component)
	{
		if (component == null) return;
		if (component == this) {
			throw new IllegalArgumentException("Uruboros. (infinite recursion evaded)");
		}
		
		components.add(component);
		addChildClient(component);
	}
	
	
	@Override
	public void renderComponent()
	{
		for (final Component cmp : components) {
			cmp.render();
		}
	}
	
	
	@Override
	public void updateLayout()
	{
		for (final Component cmp : components) {
			cmp.updateLayout();
		}
	}
	
	
	@Override
	public void setIndirectlyEnabled(boolean yes)
	{
		super.setIndirectlyEnabled(yes);
		
		for (final Component cmp : components) {
			cmp.setIndirectlyEnabled(yes);
		}
	}
}

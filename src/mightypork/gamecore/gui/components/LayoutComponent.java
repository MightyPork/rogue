package mightypork.gamecore.gui.components;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.core.modules.AppSubModule;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.eventbus.clients.ClientHub;
import mightypork.utils.math.constraints.rect.RectBound;


public abstract class LayoutComponent extends BaseComponent implements ClientHub, AppAccess {
	
	private final AppSubModule subModule;
	final LinkedList<Component> components = new LinkedList<>();
	
	
	public LayoutComponent(AppAccess app, RectBound context)
	{
		this.subModule = new AppSubModule(app);
		setRect(context);
		enableCaching(true); // layout is typically updated only when screen resizes.
	}
	
	
	public LayoutComponent(AppAccess app)
	{
		this(app, null);
	}
	
	
	@Override
	public EventBus getEventBus()
	{
		return subModule.getEventBus();
	}
	
	
	@Override
	public Collection<Object> getChildClients()
	{
		return subModule.getChildClients();
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return subModule.doesDelegate();
	}
	
	
	@Override
	public boolean isListening()
	{
		return subModule.isListening();
	}
	
	
	@Override
	public SoundSystem getSoundSystem()
	{
		return subModule.getSoundSystem();
	}
	
	
	@Override
	public InputSystem getInput()
	{
		return subModule.getInput();
	}
	
	
	@Override
	public DisplaySystem getDisplay()
	{
		return subModule.getDisplay();
	}
	
	
	@Override
	public void shutdown()
	{
		subModule.shutdown();
	}
	
	
	@Override
	public void addChildClient(Object client)
	{
		subModule.addChildClient(client);
	}
	
	
	@Override
	public void removeChildClient(Object client)
	{
		subModule.removeChildClient(client);
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
		if (component == this) throw new IllegalArgumentException("Uruboros. (infinite recursion evaded)");
		
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

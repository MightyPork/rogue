package mightypork.gamecore.gui.components;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.control.Enableable;
import mightypork.util.control.eventbus.EventBus;
import mightypork.util.control.eventbus.clients.ClientHub;
import mightypork.util.logging.Log;


public abstract class LayoutComponent extends VisualComponent implements Enableable, ClientHub, AppAccess {
	
	private boolean enabled;
	
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
	public void enable(boolean yes)
	{
		subModule.setDelegating(yes);
		subModule.setListening(yes);
		enabled = yes;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
	
	
	/**
	 * Connect to bus and add to element list
	 * 
	 * @param component added component, whose context has already been set.
	 */
	public final void attach(Component component)
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
	
}

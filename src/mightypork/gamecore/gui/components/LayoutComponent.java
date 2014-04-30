package mightypork.gamecore.gui.components;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.AppSubModule;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.clients.ClientHub;
import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


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

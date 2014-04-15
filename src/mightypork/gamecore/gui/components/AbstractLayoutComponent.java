package mightypork.gamecore.gui.components;


import java.util.Collection;
import java.util.LinkedList;

import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.math.constraints.rect.proxy.RectBound;


public abstract class AbstractLayoutComponent extends AbstractVisualComponent implements LayoutComponent, AppAccess {
	
	private boolean enabled;
	
	private final AppSubModule subModule;
	final LinkedList<Component> elements = new LinkedList<>();
	
	
	public AbstractLayoutComponent(AppAccess app, RectBound context) {
		this.subModule = new AppSubModule(app);
		setRect(context);
	}
	
	
	public AbstractLayoutComponent(AppAccess app) {
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
	 * Add element to the holder, setting it's context.<br>
	 * Element must then be then attached using the <code>attach</code> method.
	 * 
	 * @param elem element
	 */
	public abstract void add(Component elem);
	
	
	/**
	 * Connect to bus and add to element list
	 * 
	 * @param elem element; it's context will be set to the constraint.
	 */
	public final void attach(Component elem)
	{
		if (elem == null) return;
		
		elements.add(elem);
		addChildClient(elem);
	}
	
	@Override
	public void renderComponent()
	{
		for (final Component element : elements) {
			element.render();
		}
	}
	
	
	@Override
	public void updateLayout()
	{
		for (final Component element : elements) {
			element.render();
		}
	}
	
}

package mightypork.gamecore.gui.components;


import java.util.Collection;

import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.clients.ClientHub;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;


public abstract class BusEnabledPainter extends SimplePainter implements ClientHub, Component, AppAccess {
	
	private boolean enabled;
	private boolean visible = true;
	
	private final AppSubModule subModule;
	
	
	public BusEnabledPainter(AppAccess app) {
		this.subModule = new AppSubModule(app);
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
	
	
	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	
	@Override
	public boolean isVisible()
	{
		return visible;
	}
	
	
	@Override
	public final void render()
	{
		if (!visible) return;
		paint();
	}
	
	
	protected abstract void paint();
	
}

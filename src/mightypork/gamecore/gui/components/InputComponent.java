package mightypork.gamecore.gui.components;


import mightypork.gamecore.eventbus.clients.ToggleableClient;
import mightypork.gamecore.gui.Enableable;


public abstract class InputComponent extends VisualComponent implements Enableable, ToggleableClient {
	
	private boolean enabled = true;
	
	
	@Override
	public void enable(boolean yes)
	{
		this.enabled = yes;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled && isVisible();
	}
	
	
	@Override
	public boolean isListening()
	{
		return isEnabled();
	}
}

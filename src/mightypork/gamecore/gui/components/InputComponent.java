package mightypork.gamecore.gui.components;


import mightypork.util.control.Enableable;
import mightypork.util.control.eventbus.clients.ToggleableClient;


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
		return enabled;
	}
	
	
	@Override
	public boolean isListening()
	{
		return isEnabled();
	}
}

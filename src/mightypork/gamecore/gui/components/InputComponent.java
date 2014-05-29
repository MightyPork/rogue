package mightypork.gamecore.gui.components;


import mightypork.utils.eventbus.clients.ToggleableClient;
import mightypork.utils.interfaces.Enableable;


public abstract class InputComponent extends BaseComponent implements Enableable, ToggleableClient {
	
	@Override
	public boolean isListening()
	{
		return isEnabled();
	}
}

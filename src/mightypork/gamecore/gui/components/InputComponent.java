package mightypork.gamecore.gui.components;


import mightypork.gamecore.eventbus.clients.ToggleableClient;
import mightypork.gamecore.gui.Enableable;


public abstract class InputComponent extends BaseComponent implements Enableable, ToggleableClient {
	
	@Override
	public boolean isListening()
	{
		return isEnabled();
	}
}

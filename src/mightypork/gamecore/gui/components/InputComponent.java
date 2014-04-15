package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.bus.clients.ToggleableClient;
import mightypork.gamecore.control.interf.Enableable;
import mightypork.gamecore.gui.ActionTrigger;


public interface InputComponent extends Component, Enableable, ActionTrigger, ToggleableClient {
	
}

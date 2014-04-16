package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.interf.Enableable;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionTrigger;


public abstract class InputComponent extends VisualComponent implements Enableable, ActionTrigger, MouseButtonEvent.Listener {
	
	private boolean enabled;
	private Action action;
	
	
	@Override
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	
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
	public abstract void renderComponent();
	
	protected void triggerAction() {
		action.run();
	}
}

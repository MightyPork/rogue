package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.events.MouseButtonEvent;
import mightypork.util.control.Action;
import mightypork.util.control.ActionTrigger;
import mightypork.util.control.Enableable;


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
	
	
	protected void triggerAction()
	{
		if (action != null) action.run();
	}
}

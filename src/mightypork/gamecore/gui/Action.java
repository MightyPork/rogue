package mightypork.gamecore.gui;


import mightypork.gamecore.control.interf.Enableable;


/**
 * Triggered action
 * 
 * @author MightyPork
 */
public abstract class Action implements Runnable, Enableable {
	
	private boolean enabled = true;
	
	
	/**
	 * Enable the action
	 * 
	 * @param enable true to enable
	 */
	@Override
	public final void enable(boolean enable)
	{
		this.enabled = enable;
	}
	
	
	/**
	 * @return true if this action is enabled.
	 */
	@Override
	public final boolean isEnabled()
	{
		return enabled;
	}
	
	
	@Override
	public final void run()
	{
		if (enabled) execute();
	}
	
	
	/**
	 * Do the work.
	 */
	public abstract void execute();
	
}

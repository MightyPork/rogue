package mightypork.gamecore.gui;


/**
 * Triggered action
 * 
 * @author MightyPork
 */
public abstract class Action implements Runnable {
	
	private boolean enabled = true;
	
	
	/**
	 * Enable the action
	 * 
	 * @param enable true to enable
	 */
	public void setEnabled(boolean enable)
	{
		this.enabled = enable;
	}
	
	
	/**
	 * @return true if this action is enabled.
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
	
	
	@Override
	public void run()
	{
		if (enabled) execute();
	}
	
	
	/**
	 * Do the work.
	 */
	public abstract void execute();
	
}

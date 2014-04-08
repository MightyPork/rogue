package mightypork.gamecore.input;


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
	public final void enable(boolean enable)
	{
		this.enabled = enable;
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

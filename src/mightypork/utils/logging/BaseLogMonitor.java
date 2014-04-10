package mightypork.utils.logging;


import java.util.logging.Level;


public abstract class BaseLogMonitor implements LogMonitor {
	
	private boolean enabled = true;
	private Level accepted = Level.ALL;
	
	
	@Override
	public void onMessageLogged(Level level, String message)
	{
		if (!enabled) return;
		if (accepted.intValue() > level.intValue()) return;
		
		logMessage(level, message);
	}
	
	
	protected abstract void logMessage(Level level, String message);
	
	
	@Override
	public void setLevel(Level level)
	{
		this.accepted = level;
	}
	
	
	@Override
	public void enable(boolean flag)
	{
		this.enabled = flag;
	}
	
}

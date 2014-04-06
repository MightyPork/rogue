package mightypork.utils.logging;


import java.util.logging.Level;


public class LogToSysoutMonitor implements LogMonitor {
	
	private boolean enabled = true;
	private Level accepted = Level.ALL;
	
	
	public void setLevel(Level level)
	{
		this.accepted = level;
	}
	
	
	@Override
	public void onMessageLogged(Level level, String message)
	{
		if (!enabled) return;
		if (accepted.intValue() > level.intValue()) return;
		
		if (level == Level.SEVERE || level == Level.WARNING) {
			System.err.print(message);
		} else {
			System.out.print(message);
		}
	}
	
	
	public void enable(boolean enable)
	{
		this.enabled = enable;
	}
	
}

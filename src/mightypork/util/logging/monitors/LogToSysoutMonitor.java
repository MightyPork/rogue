package mightypork.util.logging.monitors;


import java.util.logging.Level;


public class LogToSysoutMonitor extends BaseLogMonitor {
	
	@Override
	protected void logMessage(Level level, String message)
	{
		if (level == Level.SEVERE || level == Level.WARNING) {
			System.err.print(message);
		} else {
			System.out.print(message);
		}
	}
	
}

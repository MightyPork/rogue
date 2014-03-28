package mightypork.utils.logging;


import java.util.logging.Level;


public class LogToSysoutMonitor implements LogMonitor {

	private boolean enabled = true;


	@Override
	public void log(Level level, String message)
	{
		if (!enabled) return;

		if (level == Level.FINE || level == Level.FINER || level == Level.FINEST || level == Level.INFO) {
			System.out.print(message);
		} else if (level == Level.SEVERE || level == Level.WARNING) {
			System.err.print(message);
		}
	}


	@Override
	public void enable(boolean enable)
	{
		this.enabled = enable;
	}

}

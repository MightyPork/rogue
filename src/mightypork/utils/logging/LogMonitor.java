package mightypork.utils.logging;


import java.util.logging.Level;


public interface LogMonitor {

	public void log(Level level, String message);


	public void enable(boolean enable);
}

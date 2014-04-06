package mightypork.utils.logging;


import java.util.logging.Level;


/**
 * Log monitor, receives all logged messages
 * 
 * @author MightyPork
 */
public interface LogMonitor {
	
	/**
	 * Message logged;
	 * 
	 * @param level message level
	 * @param message message text, already formatted.
	 */
	void onMessageLogged(Level level, String message);
}

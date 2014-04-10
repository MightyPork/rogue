package mightypork.utils.logging;


import java.util.logging.Level;


public interface LogMonitor {
	
	void onMessageLogged(Level level, String message);
	
	
	void setLevel(Level level);
	
	
	void enable(boolean flag);
	
}

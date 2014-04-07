package mightypork.utils.logging;


import java.io.File;
import java.util.HashMap;


public class Log {
	
	/** enable static logging */
	private static boolean staticLogging = true;
	
	
	/**
	 * Log FINE message in main logger
	 * 
	 * @param msg message
	 */
	public static void f1(String msg)
	{
		if (staticLogging && ready()) main.f1(msg);
	}
	
	
	/**
	 * Log FINER message in main logger
	 * 
	 * @param msg message
	 */
	public static void f2(String msg)
	{
		if (staticLogging && ready()) main.f2(msg);
	}
	
	
	/**
	 * Log FINEST message in main logger
	 * 
	 * @param msg message
	 */
	public static void f3(String msg)
	{
		if (staticLogging && ready()) main.f3(msg);
	}
	
	
	/**
	 * Log INFO message in main logger
	 * 
	 * @param msg message
	 */
	public static void i(String msg)
	{
		if (staticLogging && ready()) main.i(msg);
	}
	
	
	/**
	 * Log WARNING message in main logger
	 * 
	 * @param msg message
	 */
	public static void w(String msg)
	{
		if (staticLogging && ready()) main.w(msg);
	}
	
	
	/**
	 * Log ERROR message in main logger
	 * 
	 * @param msg message
	 */
	public static void e(String msg)
	{
		if (staticLogging && ready()) main.e(msg);
	}
	
	
	/**
	 * Log EXCEPTION and ERROR message in main logger
	 * 
	 * @param msg message
	 * @param thrown thrown exception
	 */
	public static void e(String msg, Throwable thrown)
	{
		if (staticLogging && ready()) main.e(msg, thrown);
	}
	
	
	/**
	 * Log EXCEPTION in main logger
	 * 
	 * @param thrown thrown exception
	 */
	public static void e(Throwable thrown)
	{
		if (staticLogging && ready()) main.e(thrown);
	}
	
	
	public static void enable(boolean flag)
	{
		if (staticLogging && ready()) main.enable(flag);
	}
	
	
	/**
	 * Enable / disable static log delegate methods
	 * 
	 * @param flag enable
	 */
	public static void enableStaticLogging(boolean flag)
	{
		staticLogging = flag;
	}
	
	private static HashMap<String, LogInstance> logs = new HashMap<String, LogInstance>();
	private static LogInstance main = null;
	
	
	/**
	 * Create a logger. If this is the first logger made, then it'll be made
	 * available via the static methods.
	 * 
	 * @param logName log name (used for filename, must be application-unique)
	 * @param logsDir directory to store logs in
	 * @param oldLogsCount number of old logs to keep, -1 for infinite, 0 for
	 *            none.
	 * @return the created Log instance
	 */
	public static synchronized LogInstance create(String logName, File logsDir, int oldLogsCount)
	{
		if (logs.containsKey(logName)) return logs.get(logName);
		final LogInstance log = new LogInstance(logName, logsDir, oldLogsCount);
		if (main == null) main = log;
		logs.put(logName, log);
		
		return log;
	}
	
	
	/**
	 * Create a logger. If this is the first logger made, then it'll be made
	 * available via the static methods.<br>
	 * Old logs will be kept.
	 * 
	 * @param logName log name (used for filename, must be application-unique)
	 * @param logsDir directory to store logs in
	 * @return the created Log instance
	 */
	public static synchronized LogInstance create(String logName, File logsDir)
	{
		if (logs.containsKey(logName)) return logs.get(logName);
		final LogInstance log = new LogInstance(logName, logsDir, -1);
		if (main == null) main = log;
		logs.put(logName, log);
		
		return log;
	}
	
	
	public int addMonitor(LogMonitor mon)
	{
		if (!ready()) throw new IllegalStateException("Main logger not initialized.");
		
		return main.addMonitor(mon);
	}
	
	
	public void removeMonitor(int id)
	{
		if (!ready()) throw new IllegalStateException("Main logger not initialized.");
		
		main.removeMonitor(id);
	}
	
	
	public static String str(Object o)
	{
		
		boolean hasToString = false;
		
		try {
			hasToString = (o.getClass().getMethod("toString").getDeclaringClass() != Object.class);
		} catch (final Exception e) {
			// oh well..
		}
		
		if (hasToString) {
			return o.toString();
		} else {
			
			return str(o.getClass());
		}
	}
	
	
	public static String str(Class<?> cls)
	{
		LoggedName ln = cls.getAnnotation(LoggedName.class);
		if (ln != null) {
			return ln.name();
		}
		
		final Class<?> enclosing = cls.getEnclosingClass();
		
		return (enclosing == null ? "" : str(enclosing) + ".") + cls.getSimpleName();
	}
	
	
	public static boolean ready()
	{
		return main != null;
	}
}

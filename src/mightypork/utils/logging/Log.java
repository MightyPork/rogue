package mightypork.utils.logging;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.logging.Level;

import mightypork.utils.string.StringUtils;


/**
 * A log.
 * 
 * @author MightyPork
 */
public class Log {
	
	private static LogWriter main = null;
	private static boolean enabled = true;
	private static final LogToSysoutMonitor sysoMonitor = new LogToSysoutMonitor();
	private static final long start_ms = System.currentTimeMillis();
	
	private static HashMap<String, SimpleLog> logs = new HashMap<>();
	
	
	/**
	 * Create a logger. If another with the name already exists, it'll be
	 * retrieved instead of creating a new one.
	 * 
	 * @param logName log name (used for filename, should be application-unique)
	 * @param logFile log file; old logs will be kept here too.
	 * @param oldLogsCount number of old logs to keep, -1 infinite, 0 none.
	 * @return the created Log instance
	 */
	public static synchronized LogWriter create(String logName, File logFile, int oldLogsCount)
	{
		if (logs.containsKey(logName)) return logs.get(logName);
		
		final ArchivingLog log = new ArchivingLog(logName, logFile, oldLogsCount);
		log.init();
		
		logs.put(logName, log);
		
		return log;
	}
	
	
	/**
	 * Create a logger. If another with the name already exists, it'll be
	 * retrieved instead of creating a new one.
	 * 
	 * @param logName log name (used for filename, must be application-unique)
	 * @param logFile log file; old logs will be kept here too.
	 * @return the created Log instance
	 */
	public static synchronized LogWriter create(String logName, File logFile)
	{
		if (logs.containsKey(logName)) return logs.get(logName);
		
		final SimpleLog log = new SimpleLog(logName, logFile);
		log.init();
		
		logs.put(logName, log);
		
		return log;
	}
	
	
	public static void setMainLogger(LogWriter log)
	{
		main = log;
	}
	
	
	public static void addMonitor(LogMonitor mon)
	{
		assertInited();
		
		main.addMonitor(mon);
	}
	
	
	public static void removeMonitor(LogMonitor mon)
	{
		assertInited();
		
		main.removeMonitor(mon);
	}
	
	
	private static void assertInited()
	{
		if (main == null) throw new IllegalStateException("Main logger not initialized.");
	}
	
	
	/**
	 * Log a message
	 * 
	 * @param level message level
	 * @param msg message text
	 */
	public static void log(Level level, String msg)
	{
		if (enabled) {
			sysoMonitor.onMessageLogged(level, formatMessage(level, msg, null, start_ms));
			
			if (main != null) {
				main.log(level, msg);
			}
		}
	}
	
	
	/**
	 * Log a message
	 * 
	 * @param level message level
	 * @param msg message text
	 * @param t thrown exception
	 */
	public static void log(Level level, String msg, Throwable t)
	{
		if (enabled) {
			sysoMonitor.onMessageLogged(level, formatMessage(level, msg, t, start_ms));
			
			if (main != null) {
				main.log(level, msg, t);
			}
		}
	}
	
	
	/**
	 * Log FINE message
	 * 
	 * @param msg message
	 */
	public static void f1(String msg)
	{
		log(Level.FINE, msg);
	}
	
	
	/**
	 * Log FINER message
	 * 
	 * @param msg message
	 */
	public static void f2(String msg)
	{
		log(Level.FINER, msg);
	}
	
	
	/**
	 * Log FINEST message
	 * 
	 * @param msg message
	 */
	public static void f3(String msg)
	{
		log(Level.FINEST, msg);
	}
	
	
	/**
	 * Log INFO message
	 * 
	 * @param msg message
	 */
	public static void i(String msg)
	{
		log(Level.INFO, msg);
	}
	
	
	/**
	 * Log WARNING message (less severe than ERROR)
	 * 
	 * @param msg message
	 */
	public static void w(String msg)
	{
		log(Level.WARNING, msg);
	}
	
	
	/**
	 * Log ERROR message
	 * 
	 * @param msg message
	 */
	public static void e(String msg)
	{
		log(Level.SEVERE, msg);
	}
	
	
	/**
	 * Log THROWING message
	 * 
	 * @param msg message
	 * @param thrown thrown exception
	 */
	public static void e(String msg, Throwable thrown)
	{
		log(Level.SEVERE, msg, thrown);
	}
	
	
	/**
	 * Log exception thrown
	 * 
	 * @param thrown thrown exception
	 */
	public static void e(Throwable thrown)
	{
		log(Level.SEVERE, null, thrown);
	}
	
	
	public static void enable(boolean flag)
	{
		enabled = flag;
	}
	
	
	public static void setSysoutLevel(Level level)
	{
		sysoMonitor.setLevel(level);
	}
	
	
	public static void setLevel(Level level)
	{
		assertInited();
		
		main.setLevel(level);
	}
	
	
	/**
	 * Get stack trace from throwable
	 * 
	 * @param t
	 * @return trace
	 */
	public static String getStackTrace(Throwable t)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	
	
	static String formatMessage(Level level, String message, Throwable throwable, long start_ms)
	{
		
		final String nl = System.getProperty("line.separator");
		
		if (message.equals("\n")) {
			return nl;
		}
		
		if (message.charAt(0) == '\n') {
			message = nl + message.substring(1);
		}
		
		final long time_ms = (System.currentTimeMillis() - start_ms);
		final double time_s = time_ms / 1000D;
		final String time = String.format("%6.2f ", time_s);
		final String time_blank = StringUtils.repeat(" ", time.length());
		
		String prefix = "[ ? ]";
		
		if (level == Level.FINE) {
			prefix = "[ # ] ";
		} else if (level == Level.FINER) {
			prefix = "[ - ] ";
		} else if (level == Level.FINEST) {
			prefix = "[   ] ";
		} else if (level == Level.INFO) {
			prefix = "[ i ] ";
		} else if (level == Level.SEVERE) {
			prefix = "[!E!] ";
		} else if (level == Level.WARNING) {
			prefix = "[!W!] ";
		}
		
		message = time + prefix + message.replaceAll("\n", nl + time_blank + prefix) + nl;
		
		if (throwable != null) {
			message += getStackTrace(throwable);
		}
		
		return message;
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
		final LoggedName ln = cls.getAnnotation(LoggedName.class);
		if (ln != null) {
			return ln.name();
		}
		
		String name = cls.getName();
		
		String sep = "";
		
		if (name.contains("$")) {
			name = name.substring(name.lastIndexOf("$") + 1);
			sep = "$";
		} else {
			name = name.substring(name.lastIndexOf(".") + 1);
			sep = ".";
		}
		
		final Class<?> enclosing = cls.getEnclosingClass();
		
		return (enclosing == null ? "" : str(enclosing) + sep) + name;
	}
}

package mightypork.utils.logging;


import java.io.File;
import java.io.FileFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import mightypork.utils.files.FileUtils;
import mightypork.utils.string.StringUtils;


/**
 * Static logger class.
 * 
 * @author MightyPork
 * @copy (c) 2014
 */
public class LogInstance {
	
	/** log file */
	private final File file;
	
	/** Log name */
	private final String name;
	
	/** Number of old logs to keep */
	private final int logs_to_keep;
	
	/** Logs dir */
	private final File log_dir;
	
	/** Logger instance. */
	private Logger logger;
	
	/** Logging enabled */
	private boolean enabled = true;
	
	private boolean sysout = true;
	
	private int monitorId = 0;
	private final HashMap<Integer, LogMonitor> monitors = new HashMap<Integer, LogMonitor>();
	
	private LogToSysoutMonitor sysoutMonitor;
	
	private long started_ms;
	
	
	/**
	 * Log
	 * 
	 * @param name log name
	 * @param dir log directory
	 * @param oldLogCount number of old log files to keep: -1 all, 0 none.
	 */
	public LogInstance(String name, File dir, int oldLogCount) {
		this.name = name;
		this.file = new File(dir, name + getSuffix());
		this.log_dir = dir;
		this.logs_to_keep = oldLogCount;
		this.started_ms = System.currentTimeMillis();
		
		init();
	}
	
	
	/**
	 * Prepare logs for logging
	 */
	private void init()
	{
		logger = Logger.getLogger(name);
		
		cleanLoggingDirectory();
		
		FileHandler handler = null;
		
		try {
			handler = new FileHandler(file.getPath());
		} catch (final Exception e) {
			throw new RuntimeException("Failed to init log", e);
		}
		
		handler.setFormatter(new LogFormatter());
		logger.addHandler(handler);
		
		enabled = true;
		
		sysoutMonitor = new LogToSysoutMonitor();
		
		addMonitor(sysoutMonitor);
		
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		logger.info("Logger \""+name+"\" initialized.");
		logger.info((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()));
	}
	
	
	private void cleanLoggingDirectory()
	{
		if (logs_to_keep == 0) return; // overwrite
			
		// move old file
		for (final File f : FileUtils.listDirectory(file.getParentFile())) {
			if (!f.isFile()) continue;
			if (f.equals(file)) {
				
				final Date d = new Date(f.lastModified());
				final String fbase = name + '_' + (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(d);
				final String suff = getSuffix();
				String cntStr = "";
				File f2;
				
				for (int cnt = 0; (f2 = new File(log_dir, fbase + cntStr + suff)).exists(); cntStr = "_" + (++cnt)) {}
				
				f.renameTo(f2);
			}
		}
		
		if (logs_to_keep == -1) return; // keep all
			
		final List<File> oldLogs = FileUtils.listDirectory(log_dir, new FileFilter() {
			
			@Override
			public boolean accept(File f)
			{
				if (f.isDirectory()) return false;
				if (!f.getName().endsWith(getSuffix())) return false;
				if (!f.getName().startsWith(name)) return false;
				
				return true;
			}
			
		});
		
		Collections.sort(oldLogs, new Comparator<File>() {
			
			@Override
			public int compare(File o1, File o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		for (int i = 0; i < oldLogs.size() - logs_to_keep; i++) {
			oldLogs.get(i).delete();
		}
	}
	
	
	/**
	 * Add log monitor
	 * 
	 * @param mon monitor
	 * @return assigned ID
	 */
	public synchronized int addMonitor(LogMonitor mon)
	{
		final int id = monitorId;
		monitorId++;
		monitors.put(id, mon);
		return id;
	}
	
	
	/**
	 * Remove a monitor by ID
	 * 
	 * @param id monitor ID
	 */
	public synchronized void removeMonitor(int id)
	{
		monitors.remove(id);
	}
	
	
	public void setSysoutLevel(Level level)
	{
		sysoutMonitor.setLevel(level);
	}
	
	
	public void setFileLevel(Level level)
	{
		logger.setLevel(level);
	}
	
	
	/**
	 * Enable logging.
	 * 
	 * @param flag do enable logging
	 */
	public void enable(boolean flag)
	{
		enabled = flag;
	}
	
	
	/**
	 * Enable printing logs to sysout
	 * 
	 * @param flag do enable logging
	 */
	public void enableSysout(boolean flag)
	{
		sysout = flag;
		sysoutMonitor.enable(sysout);
	}
	
	
	public void log(Level level, String msg)
	{
		if (enabled) {
			logger.log(level, msg);
			
			String fmt = formatMessage(level, msg, null);
			
			for (final LogMonitor mon : monitors.values()) {
				mon.onMessageLogged(level, fmt);
			}
		}
	}
	
	
	public void log(Level level, String msg, Throwable t)
	{
		if (enabled) {
			logger.log(level, msg, t);
			
			String fmt = formatMessage(level, msg, null);
			
			for (final LogMonitor mon : monitors.values()) {
				mon.onMessageLogged(level, fmt);
			}
		}
	}
	
	
	/**
	 * Log FINE message
	 * 
	 * @param msg message
	 */
	public void f1(String msg)
	{
		log(Level.FINE, msg);
	}
	
	
	/**
	 * Log FINER message
	 * 
	 * @param msg message
	 */
	public void f2(String msg)
	{
		log(Level.FINER, msg);
	}
	
	
	/**
	 * Log FINEST message
	 * 
	 * @param msg message
	 */
	public void f3(String msg)
	{
		log(Level.FINEST, msg);
	}
	
	
	/**
	 * Log INFO message
	 * 
	 * @param msg message
	 */
	public void i(String msg)
	{
		log(Level.INFO, msg);
	}
	
	
	/**
	 * Log WARNING message (less severe than ERROR)
	 * 
	 * @param msg message
	 */
	public void w(String msg)
	{
		log(Level.WARNING, msg);
	}
	
	
	/**
	 * Log ERROR message
	 * 
	 * @param msg message
	 */
	public void e(String msg)
	{
		log(Level.SEVERE, msg);
	}
	
	
	/**
	 * Log THROWING message
	 * 
	 * @param msg message
	 * @param thrown thrown exception
	 */
	public void e(String msg, Throwable thrown)
	{
		log(Level.SEVERE, msg);
	}
	
	
	/**
	 * Log exception thrown
	 * 
	 * @param thrown thrown exception
	 */
	public void e(Throwable thrown)
	{
		log(Level.SEVERE, null, thrown);
	}
	
	/**
	 * PowerCraft Log file formatter.
	 * 
	 * @author MightyPork
	 * @copy (c) 2012
	 */
	private class LogFormatter extends Formatter {
		
		@Override
		public String format(LogRecord record)
		{
			return LogInstance.this.formatMessage(record.getLevel(), record.getMessage(), record.getThrown());
		}
	}
	
	
	/**
	 * @return log filename suffix (incl. dot)
	 */
	protected String getSuffix()
	{
		return ".log";
	}
	
	
	private String formatMessage(Level level, String message, Throwable throwable)
	{
		
		final String nl = System.getProperty("line.separator");
		
		if (message.equals("\n")) {
			return nl;
		}
		
		if (message.charAt(0) == '\n') {
			message = nl + message.substring(1);
		}
		
		long time_ms = (System.currentTimeMillis()-started_ms);
		double time_s = time_ms / 1000D;
		String time = String.format("%6.2f ", time_s);
		String time_blank = StringUtils.repeat(" ", time.length());
		
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
	
	
	/**
	 * Get stack trace from throwable
	 * 
	 * @param t
	 * @return trace
	 */
	private static String getStackTrace(Throwable t)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}

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


/**
 * Static logger class.
 * 
 * @author MightyPork
 * @copy (c) 2014
 */
public class LogInstance {
	
	/** log file */
	private File file;
	
	/** Log name */
	private String name;
	
	/** Number of old logs to keep */
	private int logs_to_keep;
	
	/** Logs dir */
	private File dir;
	
	/** Logger instance. */
	private Logger logger;
	
	/** Logging enabled */
	private boolean enabled = true;
	
	private boolean sysout = true;
	
	private int monitorId = 0;
	private HashMap<Integer, LogMonitor> monitors = new HashMap<Integer, LogMonitor>();
	
	private LogToSysoutMonitor sysoutMonitor;
	
	
	public LogInstance(String name, File dir, int oldLogCount) {
		this.name = name;
		this.file = new File(dir, name + getSuffix());
		this.dir = dir;
		this.logs_to_keep = oldLogCount;
		
		init();
	}
	
	
	/**
	 * Prepare logs for logging
	 */
	private void init()
	{
		logger = Logger.getLogger(name);
		
		cleanup();
		
		FileHandler handler = null;
		
		try {
			handler = new FileHandler(file.getPath());
		} catch (Exception e) {
			throw new RuntimeException("Failed to init log", e);
		}
		
		handler.setFormatter(new LogFormatter());
		logger.addHandler(handler);
		
		enabled = true;
		
		sysoutMonitor = new LogToSysoutMonitor();
		
		addMonitor(sysoutMonitor);
		
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		logger.info("Main logger initialized.");
		logger.info((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()));
	}
	
	
	private void cleanup()
	{
		if (logs_to_keep == 0) return; // overwrite
			
		for (File f : FileUtils.listDirectory(file.getParentFile())) {
			if (!f.isFile()) continue;
			if (f.equals(file)) {
				Date d = new Date(f.lastModified());
				
				String fbase = name + '_' + (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(d);
				String suff = getSuffix();
				String cntStr = "";
				File f2;
				
				for (int cnt = 0; (f2 = new File(dir, fbase + cntStr + suff)).exists(); cntStr = "_" + (++cnt));
				
				f.renameTo(f2);
			}
		}
		
		if (logs_to_keep == -1) return; // keep all
			
		List<File> oldLogs = FileUtils.listDirectory(dir, new FileFilter() {
			
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
		int id = monitorId;
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
	
	
	/**
	 * Log FINE message
	 * 
	 * @param msg message
	 */
	public void f1(String msg)
	{
		if (enabled) logger.log(Level.FINE, msg);
	}
	
	
	/**
	 * Log FINER message
	 * 
	 * @param msg message
	 */
	public void f2(String msg)
	{
		if (enabled) logger.log(Level.FINER, msg);
	}
	
	
	/**
	 * Log FINEST message
	 * 
	 * @param msg message
	 */
	public void f3(String msg)
	{
		if (enabled) logger.log(Level.FINEST, msg);
	}
	
	
	/**
	 * Log INFO message
	 * 
	 * @param msg message
	 */
	public void i(String msg)
	{
		if (enabled) logger.log(Level.INFO, msg);
	}
	
	
	/**
	 * Log WARNING message (less severe than ERROR)
	 * 
	 * @param msg message
	 */
	public void w(String msg)
	{
		if (enabled) logger.log(Level.WARNING, msg);
	}
	
	
	/**
	 * Log ERROR message
	 * 
	 * @param msg message
	 */
	public void e(String msg)
	{
		if (enabled) logger.log(Level.SEVERE, msg);
	}
	
	
	/**
	 * Log THROWING message
	 * 
	 * @param msg message
	 * @param thrown thrown exception
	 */
	public void e(String msg, Throwable thrown)
	{
		if (enabled) logger.log(Level.SEVERE, msg + "\n" + getStackTrace(thrown));
	}
	
	
	/**
	 * Log exception thrown
	 * 
	 * @param thrown thrown exception
	 */
	public void e(Throwable thrown)
	{
		if (enabled) logger.log(Level.SEVERE, getStackTrace(thrown));
	}
	
	
	/**
	 * Get stack trace from throwable
	 * 
	 * @param t
	 * @return trace
	 */
	private String getStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	
	/**
	 * PowerCraft Log file formatter.
	 * 
	 * @author MightyPork
	 * @copy (c) 2012
	 */
	private class LogFormatter extends Formatter {
		
		/** Newline string constant */
		private final String nl = System.getProperty("line.separator");
		
		
		@Override
		public String format(LogRecord record)
		{
			StringBuffer buf = new StringBuffer(180);
			
			if (record.getMessage().equals("\n")) {
				return nl;
			}
			
			if (record.getMessage().charAt(0) == '\n') {
				buf.append(nl);
				record.setMessage(record.getMessage().substring(1));
			}
			
			Level level = record.getLevel();
			String trail = "[ ? ]";
			if (level == Level.FINE) {
				trail = "[ # ] ";
			}
			if (level == Level.FINER) {
				trail = "[ - ] ";
			}
			if (level == Level.FINEST) {
				trail = "[   ] ";
			}
			if (level == Level.INFO) {
				trail = "[ i ] ";
			}
			if (level == Level.SEVERE) {
				trail = "[!E!] ";
			}
			if (level == Level.WARNING) {
				trail = "[!W!] ";
			}
			
			record.setMessage(record.getMessage().replaceAll("\n", nl + trail));
			
			buf.append(trail);
			buf.append(formatMessage(record));
			
			buf.append(nl);
			
			Throwable throwable = record.getThrown();
			if (throwable != null) {
				buf.append("at ");
				buf.append(record.getSourceClassName());
				buf.append('.');
				buf.append(record.getSourceMethodName());
				buf.append(nl);
				
				StringWriter sink = new StringWriter();
				throwable.printStackTrace(new PrintWriter(sink, true));
				buf.append(sink.toString());
				
				buf.append(nl);
			}
			
			String str = buf.toString();
			
			for (LogMonitor mon : monitors.values()) {
				mon.log(level, str);
			}
			
			return str;
		}
	}
	
	
	/**
	 * @return log filename suffix (incl. dot)
	 */
	protected String getSuffix()
	{
		return ".log";
	}
}

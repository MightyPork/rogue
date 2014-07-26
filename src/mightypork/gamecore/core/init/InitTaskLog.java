package mightypork.gamecore.core.init;


import java.io.File;
import java.util.logging.Level;

import mightypork.gamecore.core.InitTask;
import mightypork.gamecore.core.WorkDir;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.writers.LogWriter;
import mightypork.utils.string.StringUtil;


/**
 * Init main logger and console log printing.<br>
 * Must be called after workdir is initialized.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskLog extends InitTask {
	
	private String logDir = "log";
	private String logName = "runtime";
	private int archiveCount = 5;
	
	private Level levelWrite = Level.ALL;
	private Level levelPrint = Level.ALL;
	
	
	/**
	 * Set log directory (relative to workdir).<br>
	 * Defaults to "log".
	 * 
	 * @param logDir log directory.
	 */
	public void setLogDir(String logDir)
	{
		this.logDir = logDir;
	}
	
	
	/**
	 * Set log name. This name is used as a prefix for archived log files.<br>
	 * Should contain only valid filename characters.<br>
	 * Defaults to "runtime".
	 * 
	 * @param logName log name
	 */
	public void setLogName(String logName)
	{
		if (!StringUtil.isValidFilenameString(logName)) {
			throw new IllegalArgumentException("Invalid log name.");
		}
		
		this.logName = logName;
	}
	
	
	/**
	 * Set number of logs to keep in the logs directory.<br>
	 * Set to 0 to keep just the last log, -1 to keep unlimited number of logs.<br>
	 * Defaults to 5.
	 * 
	 * @param archiveCount logs to keep
	 */
	public void setArchiveCount(int archiveCount)
	{
		this.archiveCount = archiveCount;
	}
	
	
	/**
	 * Set logging levels (minimal level of message to be accepted)<br>
	 * Defaults to ALL, ALL.
	 * 
	 * @param levelWrite level for writing to file
	 * @param levelPrint level for writing to stdout / stderr
	 */
	public void setLevels(Level levelWrite, Level levelPrint)
	{
		this.levelWrite = levelWrite;
		this.levelPrint = levelPrint;
	}
	
	
	@Override
	public void run()
	{
		final LogWriter log = Log.create(logName, new File(WorkDir.getDir(logDir), logName + ".log"), archiveCount);
		Log.setMainLogger(log);
		Log.setLevel(levelWrite);
		Log.setSysoutLevel(levelPrint);
	}
	
	
	@Override
	public String getName()
	{
		return "log";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "workdir" };
	}
}

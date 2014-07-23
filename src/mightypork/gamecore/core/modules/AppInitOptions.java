package mightypork.gamecore.core.modules;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.core.WorkDir.RouteSetup;
import mightypork.gamecore.core.config.ConfigSetup;
import mightypork.gamecore.core.config.KeySetup;
import mightypork.gamecore.resources.AsyncResourceLoader;
import mightypork.gamecore.resources.ResourceLoader;
import mightypork.gamecore.resources.ResourceSetup;


/**
 * Init options holder class
 */
public class AppInitOptions {
	
	boolean singleInstance = false;
	
	Backend backend = null;
	File workdir = null;
	
	String logDir = "log";
	String logFilePrefix = "runtime";
	
	String screenshotDir = "screenshots";
	
	int logArchiveCount = 0;
	boolean busLogging = false;
	
	String configFile = "settings.cfg";
	String configComment = "Main config file";
	
	public String lockFile = ".lock";
	
	final List<ResourceSetup> resourceLists = new ArrayList<>();
	final List<KeySetup> keyLists = new ArrayList<>();
	final List<ConfigSetup> configLists = new ArrayList<>();
	final List<RouteSetup> routeLists = new ArrayList<>();
	
	ResourceLoader resourceLoader = new AsyncResourceLoader();
	Level logLevel = Level.ALL;
	public boolean sigleInstance = true;
	Level logSoutLevel = Level.ALL;
	
	
	public void setConfigFile(String filename, String comment)
	{
		configFile = filename;
		configComment = comment;
	}
	
	
	public void addConfig(ConfigSetup cfg)
	{
		configLists.add(cfg);
	}
	
	
	public void addKeys(KeySetup keys)
	{
		keyLists.add(keys);
	}
	
	
	public void addRoutes(RouteSetup keys)
	{
		routeLists.add(keys);
	}
	
	
	public void addResources(ResourceSetup res)
	{
		resourceLists.add(res);
	}
	
	
	public void setBackend(Backend backend)
	{
		this.backend = backend;
	}
	
	
	/**
	 * Set whether to run in single instance mode, or allow multiple instances.<br>
	 * Multiple instances running can cause various collisions (eg. when writing
	 * config file or logging).
	 * 
	 * @param sigleInstance true to allow only one instance
	 */
	public void setSigleInstance(boolean sigleInstance)
	{
		this.sigleInstance = sigleInstance;
	}
	
	
	/**
	 * Set working directory path. If not exists, it will be created.
	 * 
	 * @param workdir work dir path
	 */
	public void setWorkdir(File workdir)
	{
		this.workdir = workdir;
	}
	
	
	public void setBusLogging(boolean yes)
	{
		busLogging = yes;
	}
	
	
	public void setLogOptions(String logDir, String filePrefix, int archivedCount, Level logLevel)
	{
		this.logDir = logDir;
		this.logFilePrefix = filePrefix;
		this.logArchiveCount = archivedCount;
		this.logLevel = logLevel;
	}
	
	
	public void setResourceLoader(ResourceLoader resLoader)
	{
		resourceLoader = resLoader;
	}
	
	
	public void setScreenshotDir(String path)
	{
		this.screenshotDir = path;
	}
	
	
	public void setLockFile(String lockFile)
	{
		this.lockFile = lockFile;
	}
	
	
	public void setLogLevel(Level logLevel, Level soutLevel)
	{
		this.logLevel = logLevel;
		this.logSoutLevel = soutLevel;
	}
}

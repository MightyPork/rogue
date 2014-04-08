package mightypork.rogue;


import mightypork.utils.config.PropertyManager;
import mightypork.utils.logging.Log;


/**
 * Main Config class
 * 
 * @author MightyPork
 */
public class Config {
	
	private static PropertyManager mgr;
	
	// opts
	public static final int def_LAST_RUN_VERSION = 0;
	public static int LAST_RUN_VERSION;
	
	public static final boolean def_START_IN_FS = false;
	public static boolean START_IN_FS;
	
	// property keys
	private static final String PK_LAST_RUN_VERSION = "status.last_run_version";
	private static final String PK_START_IN_FS = "cfg.start_in_fullscreen";
	
	
	/**
	 * Prepare config manager and load user settings
	 */
	public static void init()
	{
		Log.f2("Initializing configuration manager.");
		
		final String comment = Const.APP_NAME + " config file";
		
		mgr = new PropertyManager(Paths.CONFIG, comment);
		
		mgr.cfgNewlineBeforeComments(true);
		mgr.cfgSeparateSections(true);
		
		mgr.putInteger(PK_LAST_RUN_VERSION, def_LAST_RUN_VERSION, null);
		mgr.putBoolean(PK_START_IN_FS, def_START_IN_FS, "Go to fullscreen on startup.");
		
		load(); // load what has been "put"
	}
	
	
	/**
	 * Save changed fields to config file
	 */
	public static void save()
	{
		mgr.setValue(PK_LAST_RUN_VERSION, Const.VERSION);
		mgr.setValue(PK_START_IN_FS, START_IN_FS);
		
		mgr.apply();
	}
	
	
	/**
	 * Load config file and assign values to fields
	 */
	public static void load()
	{
		mgr.apply();
		
		LAST_RUN_VERSION = mgr.getInteger(PK_LAST_RUN_VERSION);
		START_IN_FS = mgr.getBoolean(PK_START_IN_FS);
	}
	
	// options that can't be configured via config file
	
	public static boolean LOGGING_ENABLED = true;
	public static boolean LOG_TO_STDOUT = true;
	public static boolean SINGLE_INSTANCE = true;
	
}

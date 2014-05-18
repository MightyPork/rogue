package mightypork.rogue;


import java.io.File;

import mightypork.gamecore.util.files.OsUtils;


public final class Paths {
	
	private static final String WORKDIR_NAME = ".rogue-save";
	
	public static File WORKDIR;
	public static File LOG_FILE;
	public static File SCREENSHOTS;
	public static File CONFIG;
	public static File LOCK;
	
	public static File SAVE_SLOT_1;
	public static File SAVE_SLOT_2;
	public static File SAVE_SLOT_3;
	
	
	/**
	 * Initialize for local workdir
	 * 
	 * @param local_wd_name workdir name
	 */
	public static void init(String local_wd_name)
	{
		init(true, local_wd_name);
	}
	
	
	/**
	 * Initialize for gloal workdir
	 */
	public static void init()
	{
		init(false, WORKDIR_NAME);
	}
	
	
	private static void init(boolean local_workdir, String workdir_name)
	{
		if (local_workdir) {
			WORKDIR = new File(workdir_name);
		} else {
			WORKDIR = OsUtils.getWorkDir(workdir_name);
		}
		
		LOG_FILE = new File(WORKDIR, "runtime.log");
		
		SCREENSHOTS = new File(WORKDIR, "screenshots");
		
		CONFIG = new File(WORKDIR, "config.ini");
		
		LOCK = new File(WORKDIR, ".lock");
		
		SAVE_SLOT_1 = new File(WORKDIR, "saves/slot_1.ion");
		SAVE_SLOT_2 = new File(WORKDIR, "saves/slot_2.ion");
		SAVE_SLOT_3 = new File(WORKDIR, "saves/slot_3.ion");
	}
	
}

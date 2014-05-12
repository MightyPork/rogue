package mightypork.rogue;


import java.io.File;


public final class Paths {
	
	public static final File WORKDIR = new File("./.rogue-save");//OsUtils.getWorkDir(APPDIR_NAME);
	
	public static final File LOG_FILE = new File(WORKDIR, "runtime.log");
	
	public static final File SCREENSHOTS = new File(WORKDIR, "screenshots");
	
	public static final File CONFIG = new File(WORKDIR, "config.ini");
	
	public static final File LOCK = new File(WORKDIR, ".lock");
	
	public static final String DIR_EFFECTS = "res/sounds/effects/";
	public static final String DIR_MUSIC = "res/sounds/music/";
	public static final String DIR_LOOPS = "res/sounds/loops/";
	
	public static final File SAVE_SLOT_1 = new File(WORKDIR, "saves/slot_1.ion");
	public static final File SAVE_SLOT_2 = new File(WORKDIR, "saves/slot_2.ion");
	public static final File SAVE_SLOT_3 = new File(WORKDIR, "saves/slot_3.ion");
	
}

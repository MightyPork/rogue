package mightypork.rogue;


import java.io.File;

import mightypork.utils.files.OsUtils;


public class Paths {
	
	private static final String APPDIR_NAME = "rogue";
	
	public static final File WORKDIR = OsUtils.getWorkDir(APPDIR_NAME);
	public static final File LOGS = OsUtils.getWorkDir(APPDIR_NAME, "logs");
	public static final File SCREENSHOTS = OsUtils.getWorkDir(APPDIR_NAME, "screenshots");
	public static final File CONFIG = new File(WORKDIR, "config.ini");
	public static final File LOCK = new File(WORKDIR, ".lock");
	
	public static final String DIR_EFFECTS = "res/sounds/effects/";
	public static final String DIR_MUSIC = "res/sounds/music/";
	public static final String DIR_LOOPS = "res/sounds/loops/";
	
}

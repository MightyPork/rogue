package mightypork.rogue;


import java.io.File;
import java.util.Arrays;

import mightypork.gamecore.app.BaseApp;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.files.OsUtils;


public class Launcher {
	
	/**
	 * Launcher
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Log.f3(Arrays.toString(args));
		
		File workdir = null;
		
		try {
			boolean localWorkdir = false;
			String lwdDir = null;
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("--workdir") || args[i].equals("-w")) {
					localWorkdir = true;
					lwdDir = args[i + 1];
					i++;
				}
			}
			
			if (!localWorkdir) {
				workdir = OsUtils.getHomeWorkDir(lwdDir);
			} else {
				workdir = new File(".rogue-save");
			}
			
		} catch (final ArrayIndexOutOfBoundsException e) {
			Log.e("Malformed arguments.");
		}
		
		final BaseApp app = new RogueApp(workdir, true);
		
		app.addResources(new RogueResources());
		app.addKeys(new RogueKeys());
		app.addConfig(new RogueConfig());
		
		app.setBusLogging(false);
		app.setConfigFile("config.ini", "Rogue config file");
		app.setLogOptions("/", "runtime", 5, java.util.logging.Level.ALL);
		
		app.start();
	}
}

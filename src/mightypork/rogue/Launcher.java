package mightypork.rogue;


import java.io.File;
import java.util.Arrays;

import mightypork.gamecore.core.BaseApp;
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
		
		// configure the app
		app.opt().setBusLogging(true);
		
		app.start();
	}
}

package mightypork.rogue;


import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

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
		Log.f3("Arguments: " + Arrays.toString(args));
		
		boolean verbose = false;
		
		File workdir = null;
		
		try {
			boolean localWorkdir = false;
			String lwdDir = null;
			
			for (int i = 0; i < args.length; i++) {
				final String arg = args[i];
				
				if (arg.equals("--workdir") || arg.equals("-w")) {
					localWorkdir = true;
					lwdDir = args[i + 1];
					i++;
					continue;
				}
				
				if (arg.equals("--verbose") || arg.equals("-v")) {
					verbose = true;
					continue;
				}
			}
			
			if (!localWorkdir) {
				workdir = OsUtils.getHomeWorkDir(".rogue-save");
			} else {
				workdir = new File(lwdDir);
			}
			
		} catch (final ArrayIndexOutOfBoundsException e) {
			Log.e("Malformed arguments.");
		}
		
		final BaseApp app = new RogueApp(workdir, true);
		
		app.opt().setLogLevel(verbose ? Level.ALL : Level.FINER);
		
		app.start();
	}
}

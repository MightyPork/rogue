package mightypork.rogue;


import java.io.File;
import java.util.logging.Level;

import mightypork.gamecore.core.modules.BaseApp;
import mightypork.gamecore.util.files.OsUtils;


public class Launcher {
	
	/**
	 * Launcher
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
//		System.out.println("argv = " + Arrays.toString(args)+"\n");
		
		Level llSyso = Level.FINER;
		final Level llFile = Level.ALL;
		
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
					
				} else if (arg.equals("--silent") || arg.equals("-s")) {
					llSyso = Level.OFF;
					continue;
					
				} else if (arg.equals("--warnings") || arg.equals("-e")) {
					llSyso = Level.WARNING;
					continue;
					
				} else if (arg.equals("--debug") || arg.equals("--verbose") || arg.equals("-v")) {
					llSyso = Level.ALL;
					continue;
					
				} else if (arg.equals("--help") || arg.equals("-h")) {
					printHelp();
					System.exit(0);
					
				} else {
					System.err.println("Unknown argument: " + arg);
					printHelp();
					System.exit(1);
				}
			}
			
			if (!localWorkdir) {
				workdir = OsUtils.getHomeWorkDir(".rogue-save");
			} else {
				workdir = new File(lwdDir);
			}
			
		} catch (final Exception e) {
			System.out.println("Error parsing arguments:");
			e.printStackTrace();
			printHelp();
			System.exit(1);
		}
		
		final BaseApp app = new RogueApp(workdir, true);
		
		app.opt().setLogLevel(llFile, llSyso);
		app.opt().setBusLogging(false);//TODO temporary
		
		app.start();
	}
	
	
	private static void printHelp()
	{
		//@formatter:off
		System.out.println(
				"Arguments:\n" +
				"\t--workdir <path>, -w <path> .... specify working directory\n" +
				"\t--verbose, --debug, -v ......... print all messages\n" +
				"\t--silent, -s ................... print no messages\n" +
				"\t--warnings, -e ................. print only warning and error messages\n" +
				"\t--help, -h ..................... show this help\n");
		//@formatter:on
	}
}

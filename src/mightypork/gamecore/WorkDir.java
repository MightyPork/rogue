package mightypork.gamecore;


import java.io.File;

import mightypork.gamecore.logging.Log;


/**
 * Static application workdir accessor.
 * 
 * @author MightyPork
 */
public class WorkDir {
	
	private static File workdir;
	
	
	public static void init(File workdir)
	{
		WorkDir.workdir = workdir;
	}
	
	
	/**
	 * Get workdir folder, create if not exists.
	 * 
	 * @param path dir path relative to workdir
	 * @return dir file
	 */
	public static File getDir(String path)
	{
		final File f = new File(workdir, path);
		if (!f.exists() && !f.mkdirs()) {
			Log.w("Could not create a directory: " + f);
		}
		
		return f;
	}
	
	
	/**
	 * Get workdir file, create parent if not exists.
	 * 
	 * @param path dir path relative to workdir
	 * @return dir file
	 */
	public static File getFile(String path)
	{
		final File f = new File(workdir, path);
		
		// create the parent dir
		getDir(f.getParent());
		
		return f;
		
	}
	
	
	public static File getWorkDir()
	{
		return workdir;
	}
}

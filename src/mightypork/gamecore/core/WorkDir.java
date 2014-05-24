package mightypork.gamecore.core;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.logging.Log;


/**
 * Static application workdir accessor.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class WorkDir {
	
	/**
	 * Route configurator.
	 */
	public static interface RouteSetup {
		
		public void addRoutes(RouteOpts routeOpts);
	}
	
	/**
	 * Route configurator access
	 */
	public static class RouteOpts {
		
		public void addPath(String alias, String path)
		{
			WorkDir.addPath(alias, path);
		}
	}
	
	public static RouteOpts routeOpts = new RouteOpts();
	private static File workdir;
	private static Map<String, String> namedPaths = new HashMap<>();
	
	
	public static void init(File workdir)
	{
		WorkDir.workdir = workdir;
	}
	
	
	/**
	 * Add a path alias (dir or file)
	 * 
	 * @param alias path alias
	 * @param path path relative to workdir
	 */
	public static void addPath(String alias, String path)
	{
		namedPaths.put(alias, path);
	}
	
	
	public static void registerRoutes(RouteSetup rs)
	{
		rs.addRoutes(routeOpts);
	}
	
	
	/**
	 * Get workdir folder, create if not exists.
	 * 
	 * @param path dir path relative to workdir
	 * @return dir file
	 */
	public static File getDir(String path)
	{
		if (namedPaths.containsKey(path)) path = namedPaths.get(path);
		
		final File f = new File(workdir, path);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				Log.w("Could not create a directory: " + f + " (path: " + path + ")");
			}
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
		if (namedPaths.containsKey(path)) path = namedPaths.get(path);
		
		final File f = new File(workdir, path);
		
		// create the parent dir
		if (!f.getParent().equals(workdir)) {
			f.getParentFile().mkdirs();
		}
		
		return f;
		
	}
	
	
	/**
	 * @return the workdir File
	 */
	public static File getWorkDir()
	{
		return workdir;
	}
	
}

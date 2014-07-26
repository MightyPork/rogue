package mightypork.gamecore.core.init;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import mightypork.gamecore.core.App;
import mightypork.gamecore.core.InitTask;
import mightypork.gamecore.core.WorkDir;
import mightypork.utils.annotations.Stub;
import mightypork.utils.files.InstanceLock;
import mightypork.utils.logging.Log;


/**
 * Initializer that takes care of setting up the proper workdir.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskWorkdir extends InitTask {
	
	private File workdirPath;
	private boolean doLock;
	private String lockFile = ".lock";
	private Map<String, String> namedPaths = new HashMap<>();
	
	
	/**
	 * @param workdir path to the working directory
	 * @param lock whether to lock the directory (single instance mode)
	 */
	public InitTaskWorkdir(File workdir, boolean lock) {
		this.workdirPath = workdir;
		this.doLock = lock;
	}
	
	
	/**
	 * Set workdir root path
	 * 
	 * @param path workdir path
	 */
	public void setWorkdirPath(File path)
	{
		this.workdirPath = path;
	}
	
	
	/**
	 * Set whether the workdir should be locked when the app is running, to
	 * prevent other instances from running simultaneously.
	 * 
	 * @param lock
	 */
	public void setInstanceLock(boolean lock)
	{
		this.doLock = lock;
	}
	
	
	/**
	 * Set name of the lock file.
	 * 
	 * @param lockFile
	 */
	public void setLockFileName(String lockFile)
	{
		this.lockFile = lockFile;
	}
	
	
	/**
	 * Add a named path
	 * 
	 * @param alias path alias (snake_case)
	 * @param path path (relative to the workdir)
	 */
	public void addPath(String alias, String path)
	{
		namedPaths.put(alias, path);
	}
	
	
	@Override
	public void run()
	{
		WorkDir.init(workdirPath);
		
		// lock working directory
		if (doLock) {
			final File lock = WorkDir.getFile(lockFile);
			if (!InstanceLock.onFile(lock)) {
				onLockError();
				return;
			}
		}
		
		for (Entry<String, String> e : namedPaths.entrySet()) {
			WorkDir.addPath(e.getKey(), e.getValue());
		}
	}
	
	
	/**
	 * Called when the lock file could not be obtained (cannot write or already
	 * exists).<br>
	 * Feel free to override this method to define custom behavior.
	 */
	@Stub
	protected void onLockError()
	{
		Log.e("Could not obtain lock file.\nOnly one instance can run at a time.");
		
		//@formatter:off
		JOptionPane.showMessageDialog(
				null,
				"Another instance is already running.\n(Delete the "+lockFile +" file in the working directory to override)",
				"Lock Error",
				JOptionPane.ERROR_MESSAGE
		);
		//@formatter:on
		
		App.shutdown();
	}
	
	
	@Override
	public String getName()
	{
		return "workdir";
	}
}

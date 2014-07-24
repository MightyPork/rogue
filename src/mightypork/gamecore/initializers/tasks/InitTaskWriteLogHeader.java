package mightypork.gamecore.initializers.tasks;


import java.io.IOException;

import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.initializers.InitTask;
import mightypork.utils.logging.Log;


/**
 * initializer task that writes a system info header to the log file.<br>
 * Must be called after log is initialized.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskWriteLogHeader extends InitTask {
	
	@Override
	public void run(App app)
	{
		String txt = "";
		
		txt += "\n### SYSTEM INFO ###\n\n";
		txt += " Platform ...... " + System.getProperty("os.name") + "\n";
		txt += " Runtime ....... " + System.getProperty("java.runtime.name") + "\n";
		txt += " Java .......... " + System.getProperty("java.version") + "\n";
		txt += " Launch path ... " + System.getProperty("user.dir") + "\n";
		
		try {
			txt += " Workdir ....... " + WorkDir.getWorkDir().getCanonicalPath() + "\n";
		} catch (final IOException e) {
			Log.e(e);
		}
		
		Log.i(txt);
	}
	
	
	@Override
	public String getName()
	{
		return "log_header";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "log", "workdir" };
	}
}

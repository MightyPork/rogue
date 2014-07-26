package mightypork.gamecore.core.init;


import java.io.IOException;

import mightypork.gamecore.core.InitTask;
import mightypork.gamecore.core.OptionalInitTask;
import mightypork.gamecore.core.WorkDir;
import mightypork.utils.logging.Log;


/**
 * initializer task that writes a system info header to the log file.<br>
 * Must be called after log is initialized.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@OptionalInitTask
public class InitTaskLogHeader extends InitTask {
	
	@Override
	public void run()
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

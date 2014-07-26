package mightypork.gamecore.backend.lwjgl;


import mightypork.gamecore.core.InitTask;
import mightypork.utils.logging.writers.LogWriter;


/**
 * Initializer that redirects slick logging to main logger.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskRedirectSlickLog extends InitTask {
	
	@Override
	public void run()
	{
		LogWriter ml = mightypork.utils.logging.Log.getMainLogger();
		SlickLogRedirector slr = new SlickLogRedirector(ml);
		org.newdawn.slick.util.Log.setLogSystem(slr);
	}
	
	
	@Override
	public String getName()
	{
		return "slick_log";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "log" };
	}
}

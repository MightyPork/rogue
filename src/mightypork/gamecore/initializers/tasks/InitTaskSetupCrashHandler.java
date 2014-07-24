package mightypork.gamecore.initializers.tasks;


import java.lang.Thread.UncaughtExceptionHandler;

import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.initializers.InitTask;
import mightypork.utils.annotations.Stub;
import mightypork.utils.logging.Log;


/**
 * Add a crash handler to the app.<br>
 * For customized crash message / crash dialog etc, override the
 * uncaughtException() method.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskSetupCrashHandler extends InitTask implements UncaughtExceptionHandler {
	
	@Override
	public void run(App app)
	{
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	
	@Override
	@Stub
	public void uncaughtException(Thread thread, Throwable throwable)
	{
		Log.e("The game has crashed.", throwable);
		App.shutdown();
	}
	
	
	@Override
	public String getName()
	{
		return "crash_handler";
	}
}

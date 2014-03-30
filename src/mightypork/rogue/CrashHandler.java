package mightypork.rogue;


import java.lang.Thread.UncaughtExceptionHandler;


public class CrashHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e)
	{
		e.printStackTrace();
		App.onCrash(e);
	}

}

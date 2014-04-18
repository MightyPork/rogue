package mightypork.rogue.util;


/**
 * Utils class
 * 
 * @author MightyPork
 */
public final class Utils {
	
	public static Thread runAsThread(Runnable r)
	{
		final Thread t = new Thread(r);
		t.start();
		return t;
	}
}

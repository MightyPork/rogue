package mightypork.rogue.util;


/**
 * Utils class
 * 
 * @author MightyPork
 */
public class Utils {
	public static Thread runAsThread(Runnable r) {
		Thread t = new Thread(r);
		t.start();
		return t;
	}
}
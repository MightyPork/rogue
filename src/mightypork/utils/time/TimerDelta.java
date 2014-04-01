package mightypork.utils.time;


/**
 * Timer for delta timing
 * 
 * @author MightyPork
 */
public class TimerDelta {
	
	private long lastFrame;
	
	private static final long SECOND = 1000000000; // a million nanoseconds
	
	
	/**
	 * New delta timer
	 */
	public TimerDelta() {
		lastFrame = System.nanoTime();
	}
	
	
	/**
	 * Get current time in NS
	 * 
	 * @return current time NS
	 */
	public long getTime()
	{
		return System.nanoTime();
	}
	
	
	/**
	 * Get time since the last "getDelta()" call.
	 * 
	 * @return delta time (seconds)
	 */
	public double getDelta()
	{
		long time = getTime();
		double delta = (time - lastFrame) / (double) SECOND;
		lastFrame = time;
		return delta;
	}
}

package mightypork.utils.time;

/**
 * Class for counting FPS in games.<br>
 * This class can be used also as a simple frequency meter - output is in Hz.
 * 
 * @author MightyPork
 */
public class FpsMeter {
	
	private long frames = 0;
	private long drops = 0;
	private long lastTimeMillis = System.currentTimeMillis();
	private long lastSecFPS = 0;
	private long lastSecDrop = 0;
	
	
	/**
	 * @return current second's FPS
	 */
	public long getFPS()
	{
		return lastSecFPS;
	}
	
	
	/**
	 * Notification that frame was rendered
	 */
	public void frame()
	{
		if (System.currentTimeMillis() - lastTimeMillis > 1000) {
			lastSecFPS = frames;
			lastSecDrop = drops;
			frames = 0;
			drops = 0;
			lastTimeMillis = System.currentTimeMillis();
		}
		frames++;
	}
	
	
	/**
	 * Notification that some frames have been dropped
	 * 
	 * @param dropped
	 *            dropped frames
	 */
	public void drop(int dropped)
	{
		drops += dropped;
	}
	
	
	/**
	 * @return current second's dropped frames
	 */
	public long getDropped()
	{
		return lastSecDrop;
	}
}

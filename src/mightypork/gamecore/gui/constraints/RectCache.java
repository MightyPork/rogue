package mightypork.gamecore.gui.constraints;


import mightypork.gamecore.control.interf.Pollable;
import mightypork.utils.math.coord.Rect;


/**
 * {@link RectConstraint} cache, used to reduce CPU load with very complex
 * constraints.<br>
 * Calculates only when polled.
 * 
 * @author MightyPork
 */
public class RectCache implements RectConstraint, Pollable {
	
	private final RectConstraint observed;
	private final Rect cached = new Rect();
	
	
	/**
	 * @param observed cached constraint
	 */
	public RectCache(RectConstraint observed) {
		this.observed = observed;
		poll();
	}
	
	
	/**
	 * Create and join a poller
	 * 
	 * @param poller poller to join
	 * @param rc observed constraint
	 */
	public RectCache(Poller poller, RectConstraint rc) {
		this(rc);
		poller.add(this);
	}
	
	
	@Override
	public Rect getRect()
	{
		return cached;
	}
	
	
	@Override
	public void poll()
	{
		cached.setTo(observed.getRect());
	}
	
}

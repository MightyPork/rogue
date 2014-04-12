package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Pollable;
import mightypork.gamecore.control.timing.Poller;
import mightypork.utils.math.rect.MutableRect;
import mightypork.utils.math.rect.RectView;


/**
 * {@link RectConstraint} cache, used to reduce CPU load with very complex
 * constraints.<br>
 * Calculates only when polled.
 * 
 * @author MightyPork
 */
public class RectCache implements RectConstraint, Pollable {
	
	private final RectConstraint observed;
	private final MutableRect cached = new MutableRect();
	
	
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
	public RectView getRect()
	{
		return cached.view();
	}
	
	
	@Override
	public void poll()
	{
		cached.setTo(observed.getRect());
	}
	
}

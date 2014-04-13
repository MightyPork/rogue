package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Pollable;
import mightypork.gamecore.control.timing.Poller;
import mightypork.utils.math.rect.RectMutable;
import mightypork.utils.math.rect.RectView;


/**
 * {@link RectBound} cache, used for caching computed Rect from a complex
 * {@link RectBound}.<br>
 * Calculates only when polled.
 * 
 * @author MightyPork
 */
public class RectCache implements RectBound, Pollable {
	
	private final RectBound observed;
	private final RectMutable cached = RectMutable.zero();
	
	
	/**
	 * @param observed cached constraint
	 */
	public RectCache(RectBound observed) {
		this.observed = observed;
		poll();
	}
	
	
	/**
	 * Create and join a poller
	 * 
	 * @param poller poller to join
	 * @param rc observed constraint
	 */
	public RectCache(Poller poller, RectBound rc) {
		this(rc);
		poller.add(this);
	}
	
	
	@Override
	public RectView getRect()
	{
		return cached;
	}
	
	
	@Override
	public void poll()
	{
		cached.setTo(observed.getRect());
	}
	
}

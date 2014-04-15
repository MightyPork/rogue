package mightypork.utils.math.constraints.vect;


import mightypork.gamecore.control.timing.Pollable;


/**
 * <p>
 * Interface for constraints that support digests. Digest is a small data object
 * with final fields, typically primitive, used for procesing (such as rendering
 * or other very frequent operations).
 * </p>
 * <p>
 * Taking a digest is expensive, so if it needs to be done often and the value
 * changes are deterministic (such as, triggered by timing event or screen
 * resize), it's useful to cache the last digest and reuse it until such an
 * event occurs again.
 * </p>
 * 
 * @author MightyPork
 * @param <D> digest class
 */
public interface Digestable<D> extends Pollable {
	
	/**
	 * Take a digest. If digest caching is enabled and a digest is already
	 * cached, it should be reused instead of making a new one.
	 * 
	 * @return digest
	 */
	public D digest();
	
	
	/**
	 * <p>
	 * Toggle digest caching.
	 * </p>
	 * <p>
	 * To trigger update of the cache, call the <code>poll()</code> method.
	 * </p>
	 * 
	 * @param yes
	 */
	void enableDigestCaching(boolean yes);
	
	
	/**
	 * @return true if digest caching is enabled.
	 */
	boolean isDigestCachingEnabled();
	
	
	/**
	 * If digest caching is enabled, query for a new digest and store it in a
	 * cache variable. This method shall only be called when the constraint is
	 * expected to have changed.
	 */
	@Override
	void poll();
	
}

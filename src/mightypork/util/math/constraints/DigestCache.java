package mightypork.util.math.constraints;


/**
 * Parametrized implementation of a {@link Digestable}
 * 
 * @author MightyPork
 * @param <D> digest class
 */
public abstract class DigestCache<D> implements Digestable<D> {
	
	private D last_digest;
	private boolean caching_enabled = false;
	private boolean dirty = true;
	
	
	@Override
	public final D digest()
	{
		if (caching_enabled) {
			if (dirty || last_digest == null) {
				last_digest = createDigest();
				dirty = false;
			}
			
			return last_digest;
		}
		
		return createDigest();
	}
	
	
	/**
	 * @return fresh new digest
	 */
	protected abstract D createDigest();
	
	
	@Override
	public final void enableDigestCaching(boolean yes)
	{
		caching_enabled = yes;
		markDigestDirty(); // mark dirty
	}
	
	
	@Override
	public final boolean isDigestCachingEnabled()
	{
		return caching_enabled;
	}
	
	
	@Override
	public final void markDigestDirty()
	{
		dirty = true;
	}
	
}

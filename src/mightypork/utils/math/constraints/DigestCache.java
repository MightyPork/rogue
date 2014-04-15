package mightypork.utils.math.constraints;


/**
 * Parametrized implementation of a {@link Digestable}
 * 
 * @author MightyPork
 * @param <D> digest class
 */
public abstract class DigestCache<D> implements Digestable<D> {
	
	private D digest;
	private boolean enabled;
	private boolean dirty;
	
	
	@Override
	public final D digest()
	{
		if (enabled) {
			if (dirty || digest == null) {
				digest = createDigest();
				dirty = false;
			}
			
			return digest;
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
		enabled = yes;
		markDigestDirty(); // mark dirty
	}
	
	
	@Override
	public final boolean isDigestCachingEnabled()
	{
		return enabled;
	}
	
	
	@Override
	public final void markDigestDirty()
	{
		dirty = true;
	}
	
}

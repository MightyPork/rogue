package mightypork.util.math.constraints.num.caching;


import mightypork.util.math.constraints.ConstraintCache;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.num.mutable.NumVar;
import mightypork.util.math.constraints.num.proxy.NumAdapter;


/**
 * <p>
 * A Num cache.
 * </p>
 * <p>
 * Values are held in a caching VectVar, and digest caching is enabled by
 * default.
 * </p>
 * 
 * @author MightyPork
 */
public abstract class AbstractNumCache extends NumAdapter implements ConstraintCache<Num> {
	
	private final NumVar cache = Num.makeVar();
	private boolean inited = false;
	private boolean cachingEnabled = true;
	
	
	public AbstractNumCache()
	{
		enableDigestCaching(true); // it changes only on poll
	}
	
	
	@Override
	protected final Num getSource()
	{
		if (!inited) markDigestDirty();
		
		return (cachingEnabled ? cache : getCacheSource());
	}
	
	
	@Override
	public final void poll()
	{
		inited = true;
		
		// poll source
		final Num source = getCacheSource();
		source.markDigestDirty(); // poll cached
		
		// store source value
		cache.setTo(source);
		
		// mark my digest dirty
		markDigestDirty();
		
		onConstraintChanged();
	}
	
	
	@Override
	public abstract void onConstraintChanged();
	
	
	@Override
	public abstract Num getCacheSource();
	
	
	@Override
	public final void enableCaching(boolean yes)
	{
		cachingEnabled = yes;
		enableDigestCaching(yes);
	}
	
	
	@Override
	public final boolean isCachingEnabled()
	{
		return cachingEnabled;
	}
	
}

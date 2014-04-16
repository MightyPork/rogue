package mightypork.util.math.constraints.vect.caching;


import mightypork.util.math.constraints.ConstraintCache;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.mutable.VectVar;
import mightypork.util.math.constraints.vect.proxy.VectAdapter;


/**
 * <p>
 * A vect cache.
 * </p>
 * <p>
 * Values are held in a caching VectVar, and digest caching is enabled by
 * default.
 * </p>
 * 
 * @author MightyPork
 */
public abstract class AbstractVectCache extends VectAdapter implements ConstraintCache<Vect> {
	
	private final VectVar cache = Vect.makeVar();
	private boolean inited = false;
	private boolean cachingEnabled = true;
	
	
	public AbstractVectCache() {
		enableDigestCaching(true); // it changes only on poll
	}
	
	
	@Override
	protected final Vect getSource()
	{
		if (!inited) markDigestDirty();
		
		return (cachingEnabled ? cache : getCacheSource());
	}
	
	
	@Override
	public final void poll()
	{
		inited = true;
		
		// poll source
		final Vect source = getCacheSource();
		source.markDigestDirty(); // poll cached
		
		// store source value
		cache.setTo(source);
		
		markDigestDirty();
		
		onChange();
	}
	
	
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

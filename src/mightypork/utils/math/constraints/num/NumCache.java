package mightypork.utils.math.constraints.num;


/**
 * <p>
 * A num cache.
 * </p>
 * <p>
 * Values are held in a caching VectVar, and digest caching is enabled by
 * default.
 * </p>
 * 
 * @author MightyPork
 */
public class NumCache extends NumAdapter {
	
	private final NumVar cache = Num.makeVar();
	private final Num source;
	private boolean inited = false;
	
	
	public NumCache(Num source) {
		this.source = source;
		enableDigestCaching(true);
	}
	
	
	@Override
	protected Num getSource()
	{
		if (!inited) poll();
		
		return cache;
	}
	
	
	/**
	 * Update cached value and cached digest (if digest caching is enabled)
	 */
	@Override
	public void poll()
	{
		inited = true;
		cache.setTo(source);
		
		super.poll();
	}
	
}

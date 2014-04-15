package mightypork.utils.math.constraints.rect;


/**
 * <p>
 * A rect cache.
 * </p>
 * <p>
 * Values are held in a caching VectVar, and digest caching is enabled by
 * default.
 * </p>
 * 
 * @author MightyPork
 */
public class RectCache extends RectAdapter {
	
	private final RectVar cache = Rect.makeVar();
	private final Rect source;
	private boolean inited = false;
	
	
	public RectCache(Rect source) {
		this.source = source;
		enableDigestCaching(true);
	}
	
	
	@Override
	protected Rect getSource()
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

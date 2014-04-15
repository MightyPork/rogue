package mightypork.utils.math.constraints.vect;


public class VectCache extends VectAdapter {
	
	private final VectVar cache = Vect.makeVar();
	private final Vect source;
	private boolean inited = false;
	
	
	public VectCache(Vect source) {
		this.source = source;
	}
	
	
	@Override
	protected Vect getSource()
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

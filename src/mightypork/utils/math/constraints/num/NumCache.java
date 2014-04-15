package mightypork.utils.math.constraints.num;


public class NumCache extends NumAdapter {
	
	private final NumVar cache = Num.makeVar();
	private final Num source;
	private boolean inited = false;
	
	
	public NumCache(Num source) {
		this.source = source;
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

package mightypork.utils.math.constraints.num.caching;

import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.num.Num;


/**
 * Num cache implementation
 * 
 * @author MightyPork
 */
public class NumCache extends AbstractNumCache {
	
	private final Num source;
	
	
	public NumCache(Num source) {
		this.source = source;
	}
	
	
	@Override
	public final Num getCacheSource()
	{
		return source;
	}


	@Override
	@DefaultImpl
	public void onChange()
	{
	}
	
}

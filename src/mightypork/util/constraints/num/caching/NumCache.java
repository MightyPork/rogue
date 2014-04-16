package mightypork.util.constraints.num.caching;


import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.num.Num;


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
	public void onConstraintChanged()
	{
	}
	
}

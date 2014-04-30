package mightypork.gamecore.util.math.constraints.num.caching;


import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.num.Num;


/**
 * Num cache implementation
 * 
 * @author MightyPork
 */
public class NumCache extends AbstractNumCache {
	
	private final Num source;
	
	
	public NumCache(Num source)
	{
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

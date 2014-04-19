package mightypork.util.constraints.vect.caching;


import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.vect.Vect;


/**
 * Vect cache implementation
 * 
 * @author MightyPork
 */
public class VectCache extends AbstractVectCache {
	
	private final Vect source;
	
	
	public VectCache(Vect source)
	{
		this.source = source;
		enableDigestCaching(true);
	}
	
	
	@Override
	public Vect getCacheSource()
	{
		return source;
	}
	
	
	@Override
	@DefaultImpl
	public void onConstraintChanged()
	{
	}
}

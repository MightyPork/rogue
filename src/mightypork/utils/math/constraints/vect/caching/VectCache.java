package mightypork.utils.math.constraints.vect.caching;


import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Vect cache implementation
 * 
 * @author MightyPork
 */
public class VectCache extends AbstractVectCache {
	
	private final Vect source;
	
	
	public VectCache(Vect source) {
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
	public void onChange()
	{
	}
}

package mightypork.gamecore.util.math.constraints.vect.caching;


import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.vect.Vect;


/**
 * Vect cache implementation
 * 
 * @author Ondřej Hruška
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

package mightypork.gamecore.util.math.constraints.rect.caching;


import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Rect cache implementation
 * 
 * @author MightyPork
 */
public class RectCache extends AbstractRectCache {
	
	private final Rect source;
	
	
	public RectCache(Rect source)
	{
		this.source = source;
	}
	
	
	@Override
	public final Rect getCacheSource()
	{
		return source;
	}
	
	
	@Override
	@DefaultImpl
	public void onConstraintChanged()
	{
	}
	
}

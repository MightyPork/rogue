package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.rect.RectAdapter;


/**
 * Pluggable rect bound adapter
 * 
 * @author MightyPork
 */
public abstract class RectBoundAdapter extends RectAdapter implements PluggableRectBound {
	
	private RectBound backing = null;
	
	
	@Override
	public void setContext(RectBound rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public Rect getSource()
	{
		return backing.getRect();
	}
	
}

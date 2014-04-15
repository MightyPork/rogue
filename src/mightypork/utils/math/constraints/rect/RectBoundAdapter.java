package mightypork.utils.math.constraints.rect;


/**
 * Pluggable rect bound adapter
 * 
 * @author MightyPork
 */
public class RectBoundAdapter extends RectAdapter implements PluggableRectBound {
	
	private RectBound backing = null;
	
	
	public RectBoundAdapter() {
	}
	
	
	public RectBoundAdapter(RectBound bound) {
		backing = bound;
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public Rect getSource()
	{
		return backing.getRect();
	}
	
}
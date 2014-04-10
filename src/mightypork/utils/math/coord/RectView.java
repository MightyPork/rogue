package mightypork.utils.math.coord;


/**
 * Read only rect
 * 
 * @author MightyPork
 */
public class RectView extends Rect {
	
	public RectView(Rect observed) {
		super(observed.min.view(), observed.max.view());
	}
	
	
	@Override
	public boolean isWritable()
	{
		return false;
	}
	
	
	@Override
	public boolean isView()
	{
		return true;
	}
	
	
	@Override
	public Rect freeze()
	{
		// do nothing
		return this;
	}
	
}

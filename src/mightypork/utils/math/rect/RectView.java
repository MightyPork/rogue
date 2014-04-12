package mightypork.utils.math.rect;




/**
 * Immutable rect
 * 
 * @author MightyPork
 */
public abstract class RectView extends RectMath<RectVal> {
	
	/**
	 * Get a proxy at given rect
	 * 
	 * @param observed observed rect
	 * @return view
	 */
	public static RectView make(Rect observed) {
		return observed.view();
	}
	
	@Override
	public RectVal move(double x, double y)
	{
		return RectVal.make(getOrigin().add(x, y), getSize());
	}
	
	
	@Override
	public RectVal shrink(double left, double right, double top, double bottom)
	{
		return RectVal.make(getOrigin().add(left, top), getSize().sub(left + right, top + bottom));
	}
	
	
	@Override
	public RectVal grow(double left, double right, double top, double bottom)
	{
		return RectVal.make(getOrigin().sub(left, top), getSize().add(left + right, top + bottom));
	}
	
	
	@Override
	public RectVal round()
	{
		return RectVal.make(getOrigin().round(), getSize().round());
	}
	
}

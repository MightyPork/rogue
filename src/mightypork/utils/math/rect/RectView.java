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
	public static RectView make(Rect observed)
	{
		return observed.getView();
	}
	
	
	@Override
	public RectVal move(double x, double y)
	{
		return RectVal.make(origin().add(x, y), size());
	}
	
	
	@Override
	public RectVal shrink(double left, double right, double top, double bottom)
	{
		return RectVal.make(origin().add(left, top), size().sub(left + right, top + bottom));
	}
	
	
	@Override
	public RectVal grow(double left, double right, double top, double bottom)
	{
		return RectVal.make(origin().sub(left, top), size().add(left + right, top + bottom));
	}
	
	
	@Override
	public RectVal round()
	{
		return RectVal.make(origin().round(), size().round());
	}
	
}

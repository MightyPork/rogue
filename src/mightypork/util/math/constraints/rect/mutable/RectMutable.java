package mightypork.util.math.constraints.rect.mutable;


import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.vect.Vect;


/**
 * Mutable rectangle; operations change it's state.
 * 
 * @author MightyPork
 */
public abstract class RectMutable extends Rect {
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 */
	public void setTo(Rect rect)
	{
		setTo(rect.origin(), rect.size());
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param width new width
	 * @param height new height
	 */
	public void setTo(Vect origin, double width, double height)
	{
		setTo(origin, Vect.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param x origin.x
	 * @param y origin.y
	 * @param width new width
	 * @param height new height
	 */
	public void setTo(double x, double y, double width, double height)
	{
		setTo(Vect.make(x, y), Vect.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param size new size
	 */
	public void setTo(Vect origin, Vect size)
	{
		setOrigin(origin);
		setSize(size);
	}
	
	
	/**
	 * Set to zero
	 */
	public void reset()
	{
		setTo(Vect.ZERO, Vect.ZERO);
	}
	
	
	/**
	 * Set new origin
	 * 
	 * @param origin new origin
	 */
	public abstract void setOrigin(Vect origin);
	
	
	/**
	 * Set new size
	 * 
	 * @param size new size
	 */
	public abstract void setSize(Vect size);
	
}

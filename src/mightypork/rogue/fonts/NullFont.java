package mightypork.rogue.fonts;


import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


/**
 * Null font used where real resource could not be loaded.
 * 
 * @author MightyPork
 */
public class NullFont implements GLFont {
	
	@Override
	public void draw(String str, RGB color)
	{
		// nope
	}
	
	
	@Override
	public void draw(String str, RGB color, int startIndex, int endIndex)
	{
		// nope
	}
	
	
	@Override
	public void draw(String str)
	{
		// nope
	}
	
	
	@Override
	public Coord getNeededSpace(String str)
	{
		return Coord.zero();
	}
	
	
	@Override
	public int getHeight()
	{
		return 0;
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return 0;
	}
	
}

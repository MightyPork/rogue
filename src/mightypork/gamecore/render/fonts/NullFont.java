package mightypork.gamecore.render.fonts;


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
	public Coord getNeededSpace(String str)
	{
		return Coord.zero();
	}
	
	
	@Override
	public int getGlyphHeight()
	{
		return 0;
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return 0;
	}
	
	
	@Override
	public int getSize()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}

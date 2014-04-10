package mightypork.gamecore.render.fonts;


import mightypork.gamecore.render.textures.FilterMode;
import mightypork.utils.logging.Log;
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
		Log.w("Drawing with null font.");
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
		return 0;
	}


	@Override
	public void setFiltering(FilterMode filter)
	{
		// nope
	}


	@Override
	public FilterMode getFiltering()
	{
		return null;
	}
	
}

package mightypork.gamecore.render.fonts.impl;


import mightypork.gamecore.render.fonts.GLFont;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.vect.Vect;


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
	public Vect getNeededSpace(String str)
	{
		return Vect.ZERO;
	}
	
	
	@Override
	public int getLineHeight()
	{
		return 0;
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return 0;
	}
	
	
	@Override
	public int getFontSize()
	{
		return 0;
	}
	
}

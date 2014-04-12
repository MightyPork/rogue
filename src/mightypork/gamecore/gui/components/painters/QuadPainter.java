package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class QuadPainter extends PluggableRenderer {
	
	private final RGB colorHMinVMin;
	private final RGB colorHMaxVMin;
	private final RGB colorHMaxVMax;
	private final RGB colorHMinVMax;
	
	
	/**
	 * Painter with solid color
	 * 
	 * @param color
	 */
	public QuadPainter(RGB color) {
		this.colorHMinVMin = color;
		this.colorHMaxVMin = color;
		this.colorHMaxVMax = color;
		this.colorHMinVMax = color;
	}
	
	
	/**
	 * Painter with coloured vertices.
	 * 
	 * @param colorHMinVMin
	 * @param colorHMaxVMin
	 * @param colorHMaxVMax
	 * @param colorHMinVMax
	 */
	public QuadPainter(RGB colorHMinVMin, RGB colorHMaxVMin, RGB colorHMaxVMax, RGB colorHMinVMax) {
		this.colorHMinVMin = colorHMinVMin;
		this.colorHMaxVMin = colorHMaxVMin;
		this.colorHMaxVMax = colorHMaxVMax;
		this.colorHMinVMax = colorHMinVMax;
	}
	
	
	@Override
	public void render()
	{
		Render.quadColor(getRect(), colorHMinVMin, colorHMaxVMin, colorHMaxVMax, colorHMinVMax);
	}
	
	
	public static QuadPainter gradH(RGB colorLeft, RGB colorRight)
	{
		return new QuadPainter(colorLeft, colorRight, colorRight, colorLeft);
	}
	
	
	public static QuadPainter gradV(RGB colorTop, RGB colorBottom)
	{
		return new QuadPainter(colorTop, colorTop, colorBottom, colorBottom);
	}
	
}

package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.util.annotations.FactoryMethod;
import mightypork.util.math.color.Color;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class QuadPainter extends VisualComponent {
	
	@FactoryMethod
	public static QuadPainter gradH(Color colorLeft, Color colorRight)
	{
		return new QuadPainter(colorLeft, colorRight, colorRight, colorLeft);
	}
	
	
	@FactoryMethod
	public static QuadPainter gradV(Color colorTop, Color colorBottom)
	{
		return new QuadPainter(colorTop, colorTop, colorBottom, colorBottom);
	}
	
	private final Color colorHMinVMin;
	private final Color colorHMaxVMin;
	private final Color colorHMaxVMax;
	private final Color colorHMinVMax;
	
	
	/**
	 * Painter with solid color
	 * 
	 * @param color
	 */
	public QuadPainter(Color color) {
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
	public QuadPainter(Color colorHMinVMin, Color colorHMaxVMin, Color colorHMaxVMax, Color colorHMinVMax) {
		this.colorHMinVMin = colorHMinVMin;
		this.colorHMaxVMin = colorHMaxVMin;
		this.colorHMaxVMax = colorHMaxVMax;
		this.colorHMinVMax = colorHMinVMax;
	}
	
	
	@Override
	public void renderComponent()
	{
		Render.quadColor(getRect(), colorHMinVMin, colorHMaxVMin, colorHMaxVMax, colorHMinVMax);
	}
}

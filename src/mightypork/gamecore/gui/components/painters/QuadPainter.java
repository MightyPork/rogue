package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.core.App;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.Grad;


/**
 * Draws image in given rect
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class QuadPainter extends BaseComponent {
	
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
	
	private final Grad grad;
	
	
	/**
	 * Painter with solid color
	 * 
	 * @param color
	 */
	public QuadPainter(Color color) {
		this.grad = new Grad(color, color, color, color);
	}
	
	
	/**
	 * Painter with coloured vertices.
	 * 
	 * @param leftTop
	 * @param rightTop
	 * @param leftBottom
	 * @param rightBottom
	 */
	public QuadPainter(Color leftTop, Color rightTop, Color leftBottom, Color rightBottom) {
		this.grad = new Grad(leftTop, rightTop, rightBottom, leftBottom);
	}
	
	
	@Override
	public void renderComponent()
	{
		App.gfx().quad(getRect(), grad);
	}
}

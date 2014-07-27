package mightypork.gamecore.gui.components.input;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.var.VectVar;


/**
 * Menu-like button with shadow and push state
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TextButton extends ClickableComponent implements DynamicWidthComponent {
	
	public final TextPainter textPainter;
	
	private final VectVar offset = Vect.makeVar();
	
	public Vect offsetPassive = height().div(16).toVectXY();
	public Vect offsetOver = height().div(20).toVectXY();
	public Vect offsetUnder = height().div(32).toVectXY();
	
	private final Color color;
	
	private boolean hoverMove = true;
	
	
	public TextButton(IFont font, String text, Color color) {
		this.color = color;
		
		this.textPainter = new TextPainter(font, AlignX.CENTER, this.color, text);
		this.textPainter.setRect(this);
		this.textPainter.setShadow(RGB.BLACK_30, offset);
		textPainter.setVPaddingPercent(5);
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (isMouseOver()) {
			if (App.input().isMouseButtonDown(0)) {
				offset.setTo(offsetUnder);
			} else {
				offset.setTo(hoverMove ? offsetOver : offsetPassive);
			}
		} else {
			offset.setTo(offsetPassive);
		}
		
		textPainter.render();
	}
	
	
	/**
	 * Disable offset change on hover
	 */
	public void disableHoverEffect()
	{
		hoverMove = false;
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return textPainter.computeWidth(height);
	}
	
}

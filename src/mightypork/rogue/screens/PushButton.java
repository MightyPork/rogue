package mightypork.rogue.screens;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.mutable.VectVar;


/**
 * Menu-like button with shadow and push state
 * 
 * @author MightyPork
 */
public class PushButton extends ClickableComponent {
	
	public final TextPainter textPainter;
	
	private final VectVar offset = Vect.makeVar();
	
	public Vect offsetPassive = height().div(16).toVectXY();
	public Vect offsetOver = height().div(20).toVectXY();
	public Vect offsetUnder = height().div(32).toVectXY();
	
	private final Color color;
	
	private boolean hoverMove = true;
	
	
	public PushButton(GLFont font, String text, Color color)
	{
		this.color = color;
		
		this.textPainter = new TextPainter(font, AlignX.CENTER, this.color, text);
		this.textPainter.setRect(this);
		this.textPainter.setShadow(RGB.BLACK_30, offset);
		textPainter.setPaddingHPerc(0, 5);
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (isMouseOver()) {
			if (InputSystem.isMouseButtonDown(0)) {
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
	public void disableHover()
	{
		hoverMove = false;
	}
	
}

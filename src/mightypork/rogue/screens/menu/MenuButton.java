package mightypork.rogue.screens.menu;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.mutable.VectVar;
import mightypork.rogue.Res;


class MenuButton extends ClickableComponent {
	
	private static GLFont font = Res.getFont("thick");
	private final TextPainter painter;
	
	private final VectVar offset = Vect.makeVar();
	private final Vect offsetPassive = height().div(16).toVectXY();
	private final Vect offsetPassive2 = height().div(24).toVectXY();
	
	private final Color color;
	
	
	public MenuButton(String text, Color color)
	{
		this.color = color;
		
		this.painter = new TextPainter(font, AlignX.CENTER, this.color, text);
		this.painter.setRect(this);
		this.painter.setShadow(RGB.BLACK_30, offset);
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (isMouseOver()) {
			if (InputSystem.isMouseButtonDown(0)) {
				offset.setTo(Vect.ZERO);
			} else {
				offset.setTo(offsetPassive2);
			}
		} else {
			offset.setTo(offsetPassive);
		}
		
		painter.render();
	}
	
}

package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.mutable.VectVar;
import mightypork.rogue.Res;


class MenuButton extends ClickableComponent {
	
	private static GLFont font = Res.getFont("main_menu_button");
	private final TextPainter painter;
	private final VectVar offset = Vect.makeVar();
	private final Vect offsetActive = Vect.make(this.height().perc(-5), Num.ZERO);
	private final Color color;
	
	
	public MenuButton(String text, Color color)
	{
		this.color = color;
		
		this.painter = new TextPainter(font, AlignX.CENTER, this.color, text);
		this.painter.setRect(this.shrink(this.height().perc(8)).move(offset));
		this.painter.setShadow(Color.BLACK.withAlpha(0.3), height().div(24).toVectXY());
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (isMouseOver()) {
			offset.setTo(offsetActive);
		} else {
			offset.setTo(Vect.ZERO);
		}
		
		painter.render();
	}
	
}

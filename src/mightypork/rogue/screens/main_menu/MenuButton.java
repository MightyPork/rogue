package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.num.mutable.NumVar;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.mutable.VectVar;
import mightypork.util.math.color.Color;


class MenuButton extends ClickableComponent {
	
	private static GLFont font = Res.getFont("main_menu_button");
	private final TextPainter painter;
	private final VectVar offset = Vect.makeVar();
	private final Vect offsetActive = Vect.make(this.height().perc(-5), Num.ZERO);
	private final Color color;
	
	private final double ALPHA_OFF = 0.6;
	private final double ALPHA_ON = 1;
	
	private final double SH_ALPHA_OFF = 0.1;
	private final double SH_ALPHA_ON = 0.5;
	
	private final NumVar alphaMul = Num.makeVar(ALPHA_OFF);
	private final NumVar alphaMulSh = Num.makeVar(SH_ALPHA_OFF);
	
	
	public MenuButton(String text, Color color) {
		this.color = color.withAlpha(alphaMul);
		
		this.painter = new TextPainter(font, AlignX.CENTER, this.color, text);
		this.painter.setRect(this.shrink(this.height().perc(8)).move(offset));
		this.painter.setShadow(Color.BLACK.withAlpha(alphaMulSh), height().div(24).toVectXY());
	}
	
	
	@Override
	protected void renderComponent()
	{
		if (isMouseOver()) {
			offset.setTo(offsetActive);
			alphaMul.setTo(ALPHA_ON);
			alphaMulSh.setTo(SH_ALPHA_ON);
		} else {
			offset.setTo(Vect.ZERO);
			alphaMul.setTo(ALPHA_OFF);
			alphaMulSh.setTo(SH_ALPHA_OFF);
		}
		
		painter.render();
	}
	
}

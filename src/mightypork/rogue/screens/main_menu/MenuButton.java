package mightypork.rogue.screens.main_menu;

import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.utils.math.color.Color;


class MenuButton extends ClickableComponent {
	
	private static GLFont font = Res.getFont("main_menu_button");
	private TextPainter painter;
	
	public MenuButton(String text, Color color) {
		this.painter = new TextPainter(font, AlignX.CENTER, color, text);
		painter.setRect(this.shrink(this.height().perc(5)));
	}
	
	
	@Override
	protected void renderComponent()
	{
		painter.render();
	}
	
}

package mightypork.rogue.screens.main_menu;

import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.AlignY;
import mightypork.gamecore.gui.components.layout.VerticalFixedFlowLayout;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;


class MenuLayer extends ScreenLayer {

	public MenuLayer(Screen screen) {
		super(screen);
		
		init();
	}

	private void init()
	{
		Rect menuBox = root.shrink(root.height().min(root.width()).mul(0.1));		
		
		Num lineHeight = menuBox.height().min(menuBox.width()).mul(0.13);
		
		VerticalFixedFlowLayout layout = new VerticalFixedFlowLayout(root, menuBox, lineHeight, AlignY.TOP);
		root.add(layout);
		
		GLFont f = Res.getFont("press_start");
		layout.add(new TextPainter(f, AlignX.CENTER, RGB.WHITE, "Hello!"));
		layout.add(new TextPainter(f, AlignX.CENTER, RGB.CYAN, "Woof Woof"));
		layout.add(new TextPainter(f, AlignX.CENTER, RGB.PURPLE, "MooooOOoOO"));
		layout.add(new TextPainter(f, AlignX.CENTER, RGB.GREEN, "Bye!"));
	}

	@Override
	public int getPriority()
	{
		return 2;
	}
	
}

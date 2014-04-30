package mightypork.rogue.screens.menu;


import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.events.CrossfadeRequest;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.color.pal.COMMODORE;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;


class MenuLayer extends ScreenLayer {
	
	public MenuLayer(Screen screen) {
		super(screen);
		
		init();
	}
	
	
	private void init()
	{
		final Rect menuBox = root.shrink(Num.ZERO, root.height().mul(0.18)); //.moveY(root.height().mul(-0.03))
		
		final GridLayout layout = new GridLayout(root, menuBox, 17, 1);
		layout.enableCaching(true);
		
		final QuadPainter bg = QuadPainter.gradV(PAL16.NIGHTBLUE, PAL16.SEABLUE);
		bg.setRect(root);
		root.add(bg);
		
		root.add(layout);
		
		int r = 0;
		TextPainter tp = new TextPainter(Res.getFont("main_menu_title"), AlignX.CENTER, COMMODORE.PURPLE, "Rogue!");
		layout.put(tp, r, 0, 3, 1);
		r += 5;
		
		MenuButton btn;
		
		
		// world button
		btn = new MenuButton("World Test", PAL16.SLIMEGREEN);
		btn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("game"));
			}
		});
		layout.put(btn, r, 0, 2, 1);
		r += 3;
		
		
		// bouncy text button
		btn = new MenuButton("Bouncy Cubes Test", PAL16.CLOUDBLUE);
		btn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("test.bouncy"));
			}
		});
		layout.put(btn, r, 0, 2, 1);
		r += 3;
		
		
		// quit button
		btn = new MenuButton("Bye!", PAL16.BLOODRED);
		btn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest(null)); // null -> fade and halt
			}
		});
		layout.put(btn, r, 0, 2, 1);
		
		
		root.add(layout);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 2;
	}
	
}

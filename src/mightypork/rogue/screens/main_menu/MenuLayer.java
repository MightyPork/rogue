package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.Res;
import mightypork.rogue.screens.CrossfadeRequest;
import mightypork.util.control.Action;
import mightypork.util.math.color.COMMODORE;
import mightypork.util.math.color.PAL16;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.rect.Rect;


class MenuLayer extends ScreenLayer {
	
	public MenuLayer(BaseScreen screen)
	{
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
		
		TextPainter tp;
		MenuButton b0, b1, b2, b3, b4;
		tp = new TextPainter(Res.getFont("main_menu_title"), AlignX.CENTER, COMMODORE.PURPLE, "Rogue!");
		b0 = new MenuButton("World Test", PAL16.SLIMEGREEN);
		b1 = new MenuButton("Gradientz", PAL16.BLAZE);
		b2 = new MenuButton("Bouncy Cubes", PAL16.CLOUDBLUE);
		b3 = new MenuButton("Flying Cat", PAL16.PIGMEAT);
		b4 = new MenuButton("Bye!", PAL16.BLOODRED);
		
		int r = 0;
		
		layout.put(tp, r, 0, 3, 1);
		r += 5;
		layout.put(b0, r, 0, 2, 1);
		r += 3;
		layout.put(b1, r, 0, 2, 1);
		r += 2;
		layout.put(b2, r, 0, 2, 1);
		r += 2;
		layout.put(b3, r, 0, 2, 1);
		r += 3;
		layout.put(b4, r, 0, 2, 1);
		
		root.add(layout);
		b0.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("game_screen"));
			}
		});
		
		b1.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("test.render"));
			}
		});
		
		b2.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("test.bouncy"));
			}
		});
		
		b3.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest("test.cat"));
				
			}
		});
		
		b4.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new CrossfadeRequest(null)); // null -> fade and halt
			}
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 2;
	}
	
}

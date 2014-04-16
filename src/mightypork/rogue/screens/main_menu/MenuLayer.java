package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.control.events.ScreenRequestEvent;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.Res;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.control.Action;
import mightypork.util.math.color.PAL16;


class MenuLayer extends ScreenLayer {
	
	public MenuLayer(BaseScreen screen) {
		super(screen);
		
		init();
	}
	
	
	private void init()
	{
		final Rect menuBox = root.shrink(Num.ZERO, root.height().mul(0.18)).moveY(root.height().mul(-0.03));
		
		final GridLayout layout = new GridLayout(root, menuBox, 17, 1);
		layout.enableCaching(true);
		
		final QuadPainter bg = QuadPainter.gradV(PAL16.NIGHTBLUE, PAL16.SEABLUE);
		bg.setRect(root);
		root.add(bg);
		
		root.add(layout);
		
		TextPainter tp;
		MenuButton b1, b2, b3, b4;
		tp = new TextPainter(Res.getFont("main_menu_title"), AlignX.CENTER, PAL16.SLIMEGREEN, "Rogue!");
		b1 = new MenuButton("Gradientz", PAL16.BLAZE);
		b2 = new MenuButton("Bouncy Cubes", PAL16.NEWPOOP);
		b3 = new MenuButton("Flying Cat", PAL16.PIGMEAT);
		b4 = new MenuButton("Bye!", PAL16.BLOODRED);
		
		layout.put(tp, 1, 0, 4, 1);
		layout.put(b1, 6, 0, 2, 1);
		layout.put(b2, 8, 0, 2, 1);
		layout.put(b3, 10, 0, 2, 1);
		layout.put(b4, 13, 0, 2, 1);
		root.add(layout);
		b1.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new ScreenRequestEvent("test.render"));
			}
		});
		
		b2.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new ScreenRequestEvent("test.bouncy"));
			}
		});
		
		b3.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new ScreenRequestEvent("test.cat"));
				
			}
		});
		
		b4.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
			}
		});
	}
	
	
	@Override
	public int getPriority()
	{
		return 2;
	}
	
}

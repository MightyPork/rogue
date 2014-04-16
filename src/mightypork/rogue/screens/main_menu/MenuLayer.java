package mightypork.rogue.screens.main_menu;


import mightypork.gamecore.control.events.ScreenRequestEvent;
import mightypork.gamecore.gui.AlignY;
import mightypork.gamecore.gui.components.layout.VerticalFixedFlowLayout;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.control.Action;
import mightypork.util.math.color.Color;


class MenuLayer extends ScreenLayer {
	
	public MenuLayer(Screen screen) {
		super(screen);
		
		init();
	}
	
	
	private void init()
	{
		Rect menuBox = root.shrink(root.height().min(root.width()).mul(0.1));
		
		Num lineHeight = menuBox.height().min(menuBox.width()).mul(0.1);
		
		VerticalFixedFlowLayout layout = new VerticalFixedFlowLayout(root, menuBox, lineHeight, AlignY.TOP);
		root.add(layout);
		
		MenuButton b1, b2, b3, b4;
		layout.add(b1 = new MenuButton("Render test", Color.WHITE));
		layout.add(b2 = new MenuButton("Bouncy Cubes", Color.CYAN));
		layout.add(b3 = new MenuButton("Flying Cat", Color.MAGENTA));
		layout.add(b4 = new MenuButton("Bye!", Color.GREEN));
		
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

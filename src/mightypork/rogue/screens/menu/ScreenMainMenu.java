package mightypork.rogue.screens.menu;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.GameStateManager.GameState;
import mightypork.rogue.Res;
import mightypork.rogue.events.GameStateRequest;
import mightypork.rogue.screens.PushButton;


/**
 * Main menu screen
 * 
 * @author MightyPork
 */
public class ScreenMainMenu extends LayeredScreen {
	
	
	public ScreenMainMenu(AppAccess app)
	{
		super(app);
		
		addLayer(new MenuLayer(this));
	}
	
	/**
	 * The layer
	 * 
	 * @author MightyPork
	 */
	class MenuLayer extends ScreenLayer {
		
		public MenuLayer(Screen screen)
		{
			super(screen);
			
			init();
		}
		
		
		private void init()
		{
			final Rect menuBox = root.shrink(Num.ZERO, root.height().perc(15)).moveY(root.height().perc(-4));
			
			
			final QuadPainter bg = QuadPainter.gradV(Color.fromHex(0x007eb3), PAL16.SEABLUE);
			bg.setRect(root);
			root.add(bg);
			
			final GridLayout layout = new GridLayout(root, menuBox, 10, 1);
			layout.enableCaching(true);
			root.add(layout);
			
			int r = 0;
			final ImagePainter ip = new ImagePainter(Res.getTxQuad("logo"));
			ip.keepAspectRatio();
			layout.put(ip, r, 0, 4, 1);
			r += 5;
			
			PushButton btn;
			
			final GLFont btnFont = Res.getFont("thick");
			
			// world button
			btn = new PushButton(btnFont, "Play", PAL16.SLIMEGREEN);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new GameStateRequest(GameState.SELECT_WORLD));
				}
			});
			layout.put(btn, r, 0, 2, 1);
			r += 3;
			
			/*
			// bouncy text button
			btn = new MenuButton("Bouncy", PAL16.CLOUDBLUE);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new CrossfadeRequest("test.bouncy"));
				}
			});
			layout.put(btn, r, 0, 2, 1);
			r += 3;
			*/
			
			// quit button
			btn = new PushButton(btnFont, "Exit", PAL16.BLOODRED);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new GameStateRequest(GameState.EXIT));
				}
			});
			layout.put(btn, r, 0, 2, 1);
		}
		
		
		@Override
		public int getZIndex()
		{
			return 2;
		}
		
	}
}

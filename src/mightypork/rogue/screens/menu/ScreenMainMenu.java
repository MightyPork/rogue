package mightypork.rogue.screens.menu;


import mightypork.gamecore.core.Config;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;


/**
 * Main menu screen
 * 
 * @author MightyPork
 */
public class ScreenMainMenu extends RogueScreen {
	
	
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
			
			final RowLayout rows = new RowLayout(root, menuBox, 13);
			rows.enableCaching(true);
			root.add(rows);
			
			final LinearLayout linlayout = new LinearLayout(root, AlignX.CENTER);
			linlayout.add(new ImagePainter(Res.getTxQuad("logo")));
			rows.add(linlayout, 4);
			rows.skip(1);
			
			TextButton btn;
			
			final GLFont btnFont = Res.getFont("thick");
			
			// world button
			btn = new TextButton(btnFont, "Play", PAL16.SLIMEGREEN);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new RogueStateRequest(RogueState.SELECT_WORLD));
				}
			});
			rows.add(btn, 2);
			rows.skip(1);
			
			
			btn = new TextButton(btnFont, "Story", PAL16.CLOUDBLUE);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new RogueStateRequest(RogueState.STORY));
				}
			});
			rows.add(btn, 2);
			rows.skip(1);
			
			// quit button
			btn = new TextButton(btnFont, "Exit", PAL16.BLOODRED);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new UserQuitRequest());
				}
			});
			rows.add(btn, 2);
			
			
			bindKey(Config.getKey("general.close"), Edge.RISING, new Runnable() {
				
				@Override
				public void run()
				{
					getEventBus().send(new UserQuitRequest());
				}
			});
		}
		
		
		@Override
		public int getZIndex()
		{
			return 2;
		}
		
	}

	
	public ScreenMainMenu(AppAccess app)
	{
		super(app);
		
		addLayer(new MenuLayer(this));
	}
	
	@Override
	protected void onScreenEnter()
	{
		super.onScreenEnter();

		getSoundSystem().fadeOutAllLoops();
		Res.getSoundLoop("music.menu").fadeIn();
	}
}

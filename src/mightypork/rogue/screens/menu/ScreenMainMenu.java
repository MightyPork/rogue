package mightypork.rogue.screens.menu;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;


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
			
			final RowLayout rows = new RowLayout(root, menuBox, 10);
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
			btn = new TextButton(btnFont, "Exit", PAL16.BLOODRED);
			btn.setAction(new Action() {
				
				@Override
				protected void execute()
				{
					getEventBus().send(new RogueStateRequest(RogueState.EXIT));
				}
			});
			rows.add(btn, 2);
			
			
			bindKey(new KeyStroke(Keys.ESCAPE), Edge.RISING, new Runnable() {
				
				@Override
				public void run()
				{
					getEventBus().send(new RogueStateRequest(RogueState.EXIT));
				}
			});
		}
		
		
		@Override
		public int getZIndex()
		{
			return 2;
		}
		
	}
}

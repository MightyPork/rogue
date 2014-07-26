package mightypork.rogue.screens.select_world;


import mightypork.gamecore.WorkDir;
import mightypork.gamecore.config.Config;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.Edge;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.PAL16;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Main menu screen
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ScreenSelectWorld extends RogueScreen {
	
	public ScreenSelectWorld(AppAccess app) {
		super(app);
		
		addLayer(new WorldsLayer(this));
	}
	
	class WorldsLayer extends ScreenLayer {
		
		private WorldSlot slot1;
		private WorldSlot slot2;
		private WorldSlot slot3;
		
		
		public WorldsLayer(Screen screen) {
			super(screen);
			
			init();
		}
		
		
		private void init()
		{
			final Rect menuBox = root.shrink(root.width().perc(25), root.height().perc(30)).moveY(root.height().perc(-10));
			
			final QuadPainter bg = QuadPainter.gradV(Color.fromHex(0x007eb3), PAL16.SEABLUE);
			bg.setRect(root);
			root.add(bg);
			
			final RowLayout rows = new RowLayout(root, menuBox, 4);
			rows.enableCaching(true);
			root.add(rows);
			
			TextPainter tp;
			
			rows.add(tp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.YELLOW, "Save slot:"));
			tp.setVPaddingPercent(20);
			tp.setShadow(RGB.BLACK_50, tp.height().mul(0.6 / 8D).toVectXY());
			
			slot1 = new WorldSlot(root, WorkDir.getFile("slot1"));
			rows.add(slot1);
			
			slot2 = new WorldSlot(root, WorkDir.getFile("slot2"));
			rows.add(slot2);
			
			slot3 = new WorldSlot(root, WorkDir.getFile("slot3"));
			rows.add(slot3);
			
			// escape to quitn from here
			bindKey(Config.getKeyStroke("general.close"), Edge.RISING, new Runnable() {
				
				@Override
				public void run()
				{
					getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
				}
			});
		}
		
		
		@Override
		public int getZIndex()
		{
			return 2;
		}
		
		
		@Override
		protected void onScreenEnter()
		{
			super.onScreenEnter();
			
			Log.f3("Refreshing save slots");
			slot1.refresh();
			slot2.refresh();
			slot3.refresh();
		}
	}
}

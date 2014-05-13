package mightypork.rogue.screens.select_world;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Paths;
import mightypork.rogue.Res;


/**
 * Main menu screen
 * 
 * @author MightyPork
 */
public class ScreenSelectWorld extends LayeredScreen {
	
	
	public ScreenSelectWorld(AppAccess app)
	{
		super(app);
		
		addLayer(new WorldsLayer(this));
	}
	
	
	class WorldsLayer extends ScreenLayer {
		
		private WorldSlot slot1;
		private WorldSlot slot2;
		private WorldSlot slot3;


		public WorldsLayer(Screen screen)
		{
			super(screen);
			
			init();
		}
		
		
		private void init()
		{
			final Rect menuBox = root.shrink(root.width().perc(25), root.height().perc(20));
			
			
			final QuadPainter bg = QuadPainter.gradV(Color.fromHex(0x007eb3), PAL16.SEABLUE);
			bg.setRect(root);
			root.add(bg);
			
			final GridLayout layout = new GridLayout(root, menuBox, 7, 1);
			layout.enableCaching(true);
			root.add(layout);
			
			TextPainter tp;
			
			layout.put(tp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.YELLOW, "Save slot:"), 0, 0, 1, 1);
			tp.setPaddingHPerc(0, 20);
			tp.setShadow(RGB.BLACK_50, tp.height().mul(0.6 / 8D).toVectXY());
						
			slot1 = new WorldSlot(root, Paths.SAVE_SLOT_1);
			layout.put(slot1, 1, 0, 1, 1);
			
			slot2 = new WorldSlot(root, Paths.SAVE_SLOT_2);
			layout.put(slot2, 2, 0, 1, 1);
			
			slot3 = new WorldSlot(root, Paths.SAVE_SLOT_3);
			layout.put(slot3, 3, 0, 1, 1);
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
			
			slot1.refresh();
			slot2.refresh();
			slot3.refresh();
		}
	}
}

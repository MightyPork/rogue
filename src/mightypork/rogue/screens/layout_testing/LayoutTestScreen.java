package mightypork.rogue.screens.layout_testing;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.linear.LinearGap;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearSquare;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;


public class LayoutTestScreen extends LayeredScreen {
	
	class Layer1 extends ScreenLayer {
		
		public Layer1(Screen screen)
		{
			super(screen);
			
			final Rect testRect = root.shrink(root.width().perc(10), root.height().perc(45));
			
			final LinearLayout ll = new LinearLayout(root, AlignX.CENTER);
			ll.setRect(testRect);
			root.add(ll);
			
			ll.add(new LinearSquare(new QuadPainter(RGB.RED)));
			ll.add(new LinearGap(50));
			ll.add(new LinearSquare(new QuadPainter(RGB.ORANGE)));
			ll.add(new LinearGap(100));
			ll.add(new LinearSquare(new QuadPainter(RGB.YELLOW)));
			ll.add(new TextPainter(Res.getFont("tiny"), RGB.WHITE, "Text qjf'\"^"));
			ll.add(new LinearSquare(new QuadPainter(RGB.GREEN)));
		}
		
		
		@Override
		public int getZIndex()
		{
			return 10;
		}
		
	}
	
	
	public LayoutTestScreen(AppAccess app)
	{
		super(app);
		
		addLayer(new Layer1(this));
	}
	
}

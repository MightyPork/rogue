package mightypork.rogue.screens.test_bouncyboxes;


import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.gui.components.layout.RowHolder;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<>();
	private RowHolder layout;
	
	
	public LayerBouncyBoxes(Screen screen) {
		super(screen);
		
		bindKeyStroke(new KeyStroke(true, Keys.KEY_RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				goRight();
			}
		});
		
		bindKeyStroke(new KeyStroke(true, Keys.KEY_LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				goLeft();
			}
		});
		
		// shrink screen rect by 8% on all sides
		
		final Rect b = bounds();
		
		final Rect holder_rect = b.shrink(b.height().perc(8));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 100));
		
		for (int i = 0; i < 99; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		final TextPainter tp = new TextPainter(Res.getFont("default"), Align.LEFT, RGB.WHITE);
		tp.setText("Press \"C\" for \"Cat\" screen.");
		
		final Num shadowOffset = tp.height().div(16 * 2); // half pixel if 16px font
		
		tp.setShadow(RGB.RED, Vect.make(shadowOffset, shadowOffset));
		
		layout.add(tp);
	}
	
	
	@Override
	public void render()
	{
		layout.render();
	}
	
	
	public void goLeft()
	{
		for (final BouncyBox bbr : boxes) {
			bbr.goLeft();
		}
	}
	
	
	public void goRight()
	{
		for (final BouncyBox bbr : boxes) {
			bbr.goRight();
		}
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
}
